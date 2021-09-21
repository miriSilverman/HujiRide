package huji.postpc.year2021.hujiride.database

import android.util.Log
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.type.LatLng
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList
import huji.postpc.year2021.hujiride.Rides.Ride as ClientRide

class Database {
    private val TAG = "Database"
    val db = Firebase.firestore
    val clients = db.collection("Clients")
    val rides = db.collection("Rides")
    val groups = db.collection("Groups")


    suspend fun newClient(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        uniqueID: String
    ): Boolean {

        val newClient = Client(firstName, lastName, true, phoneNumber)
        try {
            clients.document(uniqueID).set(newClient).await()
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
        // TODO: Validate Group ID
//        val client: Client? = findClient(clientUniqueID)
//        if (client == null) {
//            return false
//        } else {
//            if (client.registeredGroups.contains(groupID)) { // if already has it, don't add again!
//                return true
//            }
//            client.registeredGroups.add(groupID)
//            clients.document(clientUniqueID).set(client).await()
//            return true
//        }
        try {
            clients.document(clientUniqueID)
                .update(mapOf(FIELD_REGTERED_GROUPS to FieldValue.arrayUnion(groupID))).await()

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
                .update(mapOf(FIELD_REGTERED_GROUPS to FieldValue.arrayRemove(groupId))).await()
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
            return false
        }
        return true
    }

    suspend fun sortRidesAccordingToALocation(latLng: LatLng): List<Ride> {
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

    suspend private fun newRide(ride: ClientRide, driverID: String): String? {
        val dbRide = Ride(
            time = ride.time,
            stops = ride.stops,
            comments = ride.comments,
            driverID = driverID,
            destName = ride.dest,
            lat = 0.0,
            long = 0.0,
            geoHash = "",
            isDestinationHuji = false
        )
        try {
            val id = UUID.randomUUID().toString()
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
        ride: ClientRide,
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
     * given groups name returns a list of the rides in this group (also "all" group)
     */
    fun getRidesListOfGroup(groupID: String): ArrayList<ClientRide> {

        return arrayListOf()
    }


    /**
     * returns the list of all the rides that the client has signed up for
     */
    suspend fun getRidesOfClient(clientUniqueID: String): ArrayList<Ride> {
        val stringRides = clients.document(clientUniqueID).get().await().get(FIELD_CLIENTS_RIDES) as ArrayList<String>

        return ArrayList(stringRides.mapNotNull { sr ->
            rides.document(sr).get().await().toObject(Ride::class.java)
        })
    }


    /**
     * return a list of the names of the groups that the client has signed up for
     */
    suspend fun getGroupsOfClient(clientUniqueID: String): ArrayList<String> {
        val groups = clients.document(clientUniqueID).get().await().get(FIELD_REGTERED_GROUPS) as List<*>
        return ArrayList(groups.mapNotNull { g -> g.toString() })
    }




}