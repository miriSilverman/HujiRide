package huji.postpc.year2021.hujiride.Rides

data class Ride (
    val src: String,
    val dest: String,
    val time: String,
    val stops: ArrayList<String>,
    val comments: ArrayList<String>,
    val drivers_first_name: String,
    val drivers_last_name: String,
    val drivers_phone_number: String,

)