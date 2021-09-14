package huji.postpc.year2021.hujiride.database

import com.google.firebase.firestore.DocumentReference
import com.google.type.LatLng

// todo: boolen is university

data class Ride (
    val time: String = "",
    val stops: ArrayList<String> = arrayListOf(),
    val comments: ArrayList<String> = arrayListOf(),
    val driverID: DocumentReference? = null,
    val destName: String = "",
    val lat: Double = 0.0,
    val long: Double = 0.0,
    val geoHash: String = "",
    @JvmField val isDestinationHuji: Boolean = true  // when true, this ride is TO HUJI. when false, this ride is FROM HUJI.
)

val FIELD_GEO_HASH = "geoHash"
val FIELD_IS_AUTH = "isAuth"