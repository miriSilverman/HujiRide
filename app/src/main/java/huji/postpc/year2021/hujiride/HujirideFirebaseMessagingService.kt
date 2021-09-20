package huji.postpc.year2021.hujiride

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class HujirideFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.d("MESSAGING", "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
    }
}