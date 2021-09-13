package huji.postpc.year2021.hujiride.database

import com.google.firebase.firestore.DocumentReference
import com.google.type.LatLng

data class Ride (
    val srcName: String = "",   // TODO: Is it really needed?
    val srcID: String = "",     // TODO: Kanal
    val destName: String = "",  // TODO: Kanal
    val destID: String = "",    // TODO: Kanal
    val time: String = "",
    val stops: ArrayList<String> = arrayListOf(),
    val comments: ArrayList<String> = arrayListOf(),
    val driverID: DocumentReference? = null,
    val lat: Double = 0.0,
    val long: Double = 0.0,
    val geoHash: String = ""
)

val FIELD_GEO_HASH = "geoHash"
val FIELD_IS_AUTH = "isAuth"