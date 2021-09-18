package huji.postpc.year2021.hujiride

data class UserDetails (
        var userFirstName: String = "",
        var userLastName: String = "",
        var userPhoneNumber: String = "",
        var allNotifications: Boolean = true,
        var justGroupNotifications: Boolean = false,

        )