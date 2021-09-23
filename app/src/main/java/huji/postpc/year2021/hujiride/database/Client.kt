package huji.postpc.year2021.hujiride.database

import com.google.firebase.firestore.DocumentReference

data class Client (
    val firstName: String = "",
    val lastName: String = "",
    @field:JvmField val isAuth: Boolean = false,
    val phoneNumber: String = "",
    val registeredGroups: ArrayList<String> = ArrayList(),
    val rides: ArrayList<DocumentReference> = ArrayList(),
    val FCMToken: String = ""
)
const val FIELD_FIRSTNAME = "firstName"
const val FIELD_LASTNAME = "lastName"
const val FIELD_PHONENUMBER = "phoneNumber"
const val FIELD_REGISTERED_GROUPS = "registeredGroups"
const val FIELD_CLIENTS_RIDES = "rides"
const val FIELD_FCM_TOKEN = "fcmtoken"
const val FIELD_IS_AUTH = "isAuth"