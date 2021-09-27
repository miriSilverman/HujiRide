package huji.postpc.year2021.hujiride.database

import android.util.Log
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.firebase.Timestamp
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.messaging.ktx.messaging
import com.google.type.LatLng
import huji.postpc.year2021.hujiride.MainActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.reflect.typeOf

class Database {
    private val TAG = "Database"
    private val db = Firebase.firestore
    private val messaging = Firebase.messaging
    private val clients = db.collection("Clients")
    private val rides = db.collection("Rides")
    private val groups = db.collection("Groups")
    private val bugs = db.collection("Bugs")

    suspend fun newClient(uniqueID: String): Boolean {
        return try {
            clients.document(uniqueID).set(mapOf(FIELD_IS_AUTH to false)).await()
            true
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            false
        }
    }

    suspend fun setClientData(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        uniqueID: String
    ): Boolean {
        try {
            clients.document(uniqueID).set(
                mapOf(
                    FIELD_FIRSTNAME to firstName,
                    FIELD_LASTNAME to lastName,
                    FIELD_PHONENUMBER to phoneNumber,
                    FIELD_IS_AUTH to true,
                ), SetOptions.merge()
            ).await()
            return true
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            return false
        }
    }

    suspend fun findClient(uniqueID: String): Client? {
        return try {
            clients.document(uniqueID).get().await().toObject(Client::class.java)
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            null
        }
    }


    suspend fun registerClientToGroup(clientUniqueID: String, groupID: String): Boolean {
        try {
            coroutineScope {
                listOf(
                    async {
                    clients.document(clientUniqueID)
                    .update(mapOf(FIELD_REGISTERED_GROUPS to FieldValue.arrayUnion(groupID)))
                    },
                    async { messaging.subscribeToTopic(groupID) }
                ).awaitAll()
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            return false
        }
        return true
    }

    /**
     * cancels registration of a client to group
     */
    suspend fun unregisterClientToGroup(clientUniqueID: String, groupId: String): Boolean {
        try {
            coroutineScope {
                listOf(
                    async { clients.document(clientUniqueID)
                        .update(mapOf(FIELD_REGISTERED_GROUPS to FieldValue.arrayRemove(groupId))) },
                    async { messaging.unsubscribeFromTopic(groupId) }
                ).awaitAll()
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            return false
        }
        return true
    }

    private val allGroup = "-1";

    suspend fun registerClientToAllNotifications(clientUniqueID: String) : Boolean {
        return try {
            messaging.subscribeToTopic(allGroup).await()
            true
        } catch (e : Exception) {
            Log.e(TAG, e.message!!)
            false
        }
    }

    suspend fun unregisterClientToAllNotifications(clientUniqueID: String) : Boolean {
        return try {
            messaging.unsubscribeFromTopic(allGroup).await()
            true
        } catch (e : Exception) {
            Log.e(TAG, e.message!!)
            false
        }
    }

    private val allNotifications = "-2"

    suspend fun disableNotifications(clientUniqueID: String) : Boolean {
        return try {
            messaging.subscribeToTopic(allNotifications).await()
            true
        } catch(e: Exception) {
            Log.e(TAG, e.message!!)
            false
        }
    }

    suspend fun reenableNotifications(clientUniqueID: String) : Boolean {
        return try {
            messaging.unsubscribeFromTopic(allNotifications).await()
            true
        } catch(e: Exception) {
            Log.e(TAG, e.message!!)
            false
        }
    }

    suspend fun sortRidesAccordingToALocation(latLng: com.google.android.gms.maps.model.LatLng): ArrayList<Ride> {
        val closeRidesSnaps = rides.orderBy(FIELD_GEO_HASH)
            .startAt(
                GeoFireUtils.getGeoHashForLocation(
                    GeoLocation(
                        latLng.latitude,
                        latLng.longitude
                    )
                )
            )
            .get()
            .await()

        val closeRides = arrayListOf<Ride>()
        for (s in closeRidesSnaps) {
            closeRides.add(s.toObject(Ride::class.java))
        }
        return closeRides.filterActiveRides()
    }

    /**
     * Returns null if the client does not even exist
     */
    suspend fun isClientAuth(clientUniqueID: String): Boolean? {
        return try {
            clients.document(clientUniqueID).get().await()?.getBoolean(FIELD_IS_AUTH)
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            null
        }
    }

    /**
     * Returns null if the client does not even exist, else, a map containing the data
     */
    suspend fun getClientData(clientUniqueID: String): MutableMap<String, Any>? {
        return try {
            clients.document(clientUniqueID).get().await()?.data
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            null
        }
    }

    private suspend fun newRide(dbRide: Ride, driverID: String): String? {
        try {
            val id = UUID.randomUUID().toString()
            dbRide.id = id
            rides.document(id).set(dbRide).await()
            return id
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            return null
        }
    }

    /**
     * adds a ride go the rideList of a certain group + the func adds the ride to the "all rides"
     * list
     */
    suspend fun addRide(ride: Ride, clientUniqueID: String, groupID: String? = null): String? {
        ride.groupID = groupID ?: "-1"
        val id = newRide(ride, clientUniqueID) ?: return null
        if (groupID == null) return id
        try {
            groups.document(groupID)
                .set(mapOf("rides" to FieldValue.arrayUnion(id)), SetOptions.merge())
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            return null
        }
        return id
    }


    /**
     * given groups id returns a list of the rides ID in this group (also "all" group)
     */
    private suspend fun getRidesIDOfGroup(groupID: String): ArrayList<String> {
        // TODO: query differently!
        try {
            return ArrayList(
                (groups.document(groupID).get().await()
                    .get("rides") as ArrayList<String>).mapNotNull { rideID -> rideID.toString() })
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            return arrayListOf()
        }
    }

    fun ArrayList<Ride>.filterActiveRides(): ArrayList<Ride> {
        return ArrayList(this.filter { ride ->
            ride.timeStamp > Timestamp(
                Date(
                    System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(
                        15
                    )
                )
            )
        })
    }

    fun <E> List<E>.toArrayList(): ArrayList<E> = ArrayList(this)

    /**
     * given groups name returns a list of the rides in this group (also "all" group)
     */
    suspend fun getRidesListOfGroup(groupID: String?): ArrayList<Ride> {
        if (groupID == null) {
            return rides.get()
                .await()
                .documents
                .mapNotNull { documentSnapshot -> documentSnapshot.toObject(Ride::class.java) }
                .toArrayList()
                .filterActiveRides()
        } else {
            return rides.whereEqualTo(FIELD_GROUP_ID, groupID)
                .get()
                .await()
                .documents
                .mapNotNull { ds -> ds.toObject(Ride::class.java) }
                .toArrayList()
                .filterActiveRides()
        }
    }


    private suspend fun fetchRides(rides: List<DocumentReference>) = coroutineScope {
        val deferreds = rides.map { ref ->
            async {
                ref.get().await()
            }
        }
        return@coroutineScope deferreds.awaitAll()
            .mapNotNull { doc -> doc.toObject(Ride::class.java) }
    }

    /**
     * returns the list of all the rides that the client has signed up for
     */
    suspend fun getRidesOfClient(clientUniqueID: String): ArrayList<Ride> {
        val refRides = (clients.document(clientUniqueID).get().await()
            ?.get(FIELD_CLIENTS_RIDES)) as List<DocumentReference>?
        return ArrayList(fetchRides(refRides.orEmpty())).filterActiveRides()
    }


    /**
     * return a list of the names of the groups that the client has signed up for
     */
    suspend fun getGroupsOfClient(clientUniqueID: String): ArrayList<String> {
        val groups =
            clients.document(clientUniqueID).get().await().get(FIELD_REGISTERED_GROUPS) as List<*>?
        return ArrayList(groups?.mapNotNull { g -> g.toString() } ?: ArrayList())
    }

    suspend fun addRideToClientsRides(clientID: String, rideID: String): Boolean {
        return try {
            clients.document(clientID)
                .update(mapOf(FIELD_CLIENTS_RIDES to FieldValue.arrayUnion(rides.document(rideID))))
                .await()
            true
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            false
        }
    }

    suspend fun addRideToClientsRides(clientId: String, ride: Ride): Boolean {
        return addRideToClientsRides(clientId, ride.id)
    }

    suspend fun setFCMToken(clientUniqueID: String, token: String): Boolean {
        return try {
            clients.document(clientUniqueID).set(hashMapOf(FIELD_FCM_TOKEN to token)).await()
            true
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            false
        }
    }

    suspend fun deleteRideFromClientsRides(clientId: String, ride: Ride): Boolean {
        return try {
            clients.document(clientId)
                .update(mapOf(FIELD_CLIENTS_RIDES to FieldValue.arrayRemove(rides.document(ride.id))))
                .await()
            true
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            false
        }
    }

    suspend fun getClientCreatedRides(clientID: String): ArrayList<Ride>? {
        return try {
            val rideDocs =
                rides.whereEqualTo(FIELD_DRIVER_ID, clientID).get().await() as QuerySnapshot
            ArrayList(rideDocs.mapNotNull { s -> (s as DocumentSnapshot).toObject(Ride::class.java) }).filterActiveRides()
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            null
        }
    }

    suspend fun deleteRide(rideId: String) : Boolean {
        return try {
            rides.document(rideId).delete().await()
            true
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            true
        }
    }

    suspend fun addBug(bug: String) : Boolean {
        return try {
            bugs.document().set(mapOf("info" to bug)).await()
            true
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            false
        }
    }
}