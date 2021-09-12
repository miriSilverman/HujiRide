package huji.postpc.year2021.hujiride.database

import com.google.firebase.firestore.DocumentReference

data class Ride (
    val src: String,
    val dest: String,
    val time: String,
    val stops: ArrayList<String>,
    val comments: ArrayList<String>,
    val driverID: DocumentReference
)