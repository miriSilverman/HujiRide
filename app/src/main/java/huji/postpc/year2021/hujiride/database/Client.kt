package huji.postpc.year2021.hujiride.database

data class Client (
    val firstName: String = "",
    val lastName: String = "",
    @field:JvmField val isAuth: Boolean = false,
    val phoneNumber: String = "",
    val registeredGroups: ArrayList<Int> = ArrayList(),
    val rides: ArrayList<String> = ArrayList(),
)

val FIELD_REGTERED_GROUPS = "registeredGroups"
val FIELD_CLIENTS_RIDES = "rides"