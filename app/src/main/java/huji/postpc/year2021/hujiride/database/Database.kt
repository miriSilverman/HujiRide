package huji.postpc.year2021.hujiride.database

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.*
import kotlin.coroutines.*
import com.google.android.gms.tasks.*
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import huji.postpc.year2021.hujiride.Rides.Ride as ClientRide

class Database {
    val db = Firebase.firestore
    val clients = db.collection("Clients")
    val rides = db.collection("Rides")

    fun print(m: String) {
        Log.d("DB FB", m)
    }

    suspend fun newClient(firstName: String, lastName: String, phoneNumber: String, uniqueID: String): Boolean {

        val newClient = Client(firstName, lastName, false, phoneNumber)
        try {
            clients.document(uniqueID).set(newClient).await()
            return true
        }catch (e: Exception) {
            return false
        }
    }

    suspend fun findClient(uniqueID: String) : Client? {
        return try {
            clients.document(uniqueID).get().await().toObject(Client::class.java)
        } catch (e: Exception) { null }
    }

    suspend fun newRide(ride: ClientRide, driverID: String): Boolean {

        val dbRide = Ride(ride.src, ride.dest, ride.time, ride.stops, ride.comments, clients.document(driverID))
        try {
            rides.document(ride.id.toString()).set(dbRide).await()
            return true
        }catch (e: Exception) {
            return false
        }
    }

    suspend fun registerClientToGroup(clientUniqueID: String, groupID: Int) : Boolean {
        // TODO: Validate Group ID
        val client: Client? = findClient(clientUniqueID)
        if (client == null) {
            return false
        } else {
            if (client.registeredGroups.contains(groupID)) { // if already has it, don't add again!
                return true
            }
            client.registeredGroups.add(groupID)
            clients.document(clientUniqueID).set(client).await()
            return true
        }
    }

}




