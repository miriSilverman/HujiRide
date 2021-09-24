package huji.postpc.year2021.hujiride.database

import android.util.Log
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.type.LatLng
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.reflect.typeOf

class Database {
    private val TAG = "Database"
    private val db = Firebase.firestore
    private val clients = db.collection("Clients")
    private val rides = db.collection("Rides")
    private val groups = db.collection("Groups")


    suspend fun setClientData(uniqueID: String) :Boolean {
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
            clients.document(uniqueID).set(mapOf(
                FIELD_FIRSTNAME to firstName,
                FIELD_LASTNAME to lastName,
                FIELD_PHONENUMBER to phoneNumber,
                FIELD_IS_AUTH to true,
            ), SetOptions.merge()).await()
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
            clients.document(clientUniqueID)
                .update(mapOf(FIELD_REGISTERED_GROUPS to FieldValue.arrayUnion(groupID))).await()
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
            clients.document(clientUniqueID)
                .update(mapOf(FIELD_REGISTERED_GROUPS to FieldValue.arrayRemove(groupId))).await()
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            return false
        }
        return true
    }

    suspend fun sortRidesAccordingToALocation(latLng: com.google.android.gms.maps.model.LatLng): List<Ride> {
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
        return closeRides
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
    suspend fun addRide(
        ride: Ride,
        clientUniqueID: String,
        groupID: String? = null
    ): Boolean {
        val id = newRide(ride, clientUniqueID) ?: return false
        if (groupID == null) return true
        try {
            groups.document(groupID)
                .set(mapOf("rides" to FieldValue.arrayUnion(id)), SetOptions.merge())
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            return false
        }
        return true
    }


    /**
     * given groups id returns a list of the rides ID in this group (also "all" group)
     */
    private suspend fun getRidesIDOfGroup(groupID: String): ArrayList<String> {
        try {
            return ArrayList((groups.document(groupID).get().await().get("rides") as ArrayList<String>).mapNotNull { rideID -> rideID.toString() })
        } catch (e : Exception) {
            Log.e(TAG, e.message!!)
            return arrayListOf()
        }
    }

    fun ArrayList<Ride>.filterActiveRides() : ArrayList<Ride>{
        return ArrayList(this.filter { ride ->
            ride.timeStamp > Timestamp(Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(15)))
        })
    }

    /**
     * given groups name returns a list of the rides in this group (also "all" group)
     */
    suspend fun getRidesListOfGroup(groupID: String?): ArrayList<Ride> {
        if (groupID == null) {
            return ArrayList(rides.get().await().documents
                .mapNotNull { documentSnapshot -> documentSnapshot.toObject(Ride::class.java) })
                .filterActiveRides()
        } else {
            return ArrayList(getRidesIDOfGroup(groupID)
                .mapNotNull {id -> rides.document(id).get().await().toObject(Ride::class.java)})
                .filterActiveRides()
        }
    }


    private suspend fun fetchRides(rides: ArrayList<DocumentReference>) = coroutineScope {
        val deferreds = rides.map { ref ->
            async {
                ref.get().await()
            }
        }
        return@coroutineScope deferreds.awaitAll().mapNotNull { doc -> doc.toObject(Ride::class.java) }
    }

    /**
     * returns the list of all the rides that the client has signed up for
     */
    suspend fun getRidesOfClient(clientUniqueID: String): ArrayList<Ride> {
        var refRides = (clients.document(clientUniqueID).get().await()?.get(FIELD_CLIENTS_RIDES)) as List<*>?
        refRides = refRides.orEmpty().map { rr -> (rr as Map<String, Any>).toRide() }
        return ArrayList(refRides).filterActiveRides()
//        return ArrayList()
    }


    /**
     * return a list of the names of the groups that the client has signed up for
     */
    suspend fun getGroupsOfClient(clientUniqueID: String): ArrayList<String> {
        val groups = clients.document(clientUniqueID).get().await().get(FIELD_REGISTERED_GROUPS) as List<*>?
        return ArrayList(groups?.mapNotNull { g -> g.toString() } ?: ArrayList())
    }


    suspend fun addRideToClientsRides(clientId: String, ride: Ride): Boolean{
        try {
            clients.document(clientId)
                .update(mapOf(FIELD_CLIENTS_RIDES to FieldValue.arrayUnion(ride))).await()
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            return false
        }
        return true



    }


    suspend fun setFCMToken(clientUniqueID: String, token: String) : Boolean {
        return try {
            clients.document(clientUniqueID).set(hashMapOf(FIELD_FCM_TOKEN to token)).await()
            true
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            false
        }
    }

    suspend fun deleteRideFromClientsRides(clientId: String, ride: Ride): Boolean{
        println("miriiiiiiiiiiiiiiiiiiii")
        return true
    }

}