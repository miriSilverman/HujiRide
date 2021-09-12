package huji.postpc.year2021.hujiride.database

data class Client (
    val firstName: String = "",
    val lastName: String = "",
    @field:JvmField val isAuth: Boolean = false,
    val phoneNumber: String = "",
    val registeredGroups: ArrayList<Int> = ArrayList()
)