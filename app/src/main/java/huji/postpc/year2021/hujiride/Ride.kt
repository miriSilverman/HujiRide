package huji.postpc.year2021.hujiride

data class Ride (
    val src: String,
    val dest: String,
    val time: String,
    val stops: ArrayList<String>,
    val comments: ArrayList<String>
)