package huji.postpc.year2021.hujiride.database

import com.google.firebase.Timestamp
import com.google.gson.Gson
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

// todo: boolen is university

val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")

fun createTimeStamp(date: String): Timestamp {
    return Timestamp(Date(dateFormat.parse(date).time))
}


data class Ride (
    val time: String = "",
    val timeStamp: Timestamp = Timestamp(Date(dateFormat.parse("31-12-2022 22:21").time)),
    val stops: ArrayList<String> = arrayListOf(),
    val comments: ArrayList<String> = arrayListOf(),
    val driverID: String = "",
    val destName: String = "",
    val lat: Double = 0.0,
    val long: Double = 0.0,
    val geoHash: String = "",
    var id: String = "",
    @JvmField val isDestinationHuji: Boolean = true,  // when true, this ride is TO HUJI. when false, this ride is FROM HUJI.
    var groupID: String = "",
)

const val FIELD_DRIVER_ID = "driverID"
const val FIELD_GEO_HASH = "geoHash"
const val FIELD_GROUP_ID = "groupID"