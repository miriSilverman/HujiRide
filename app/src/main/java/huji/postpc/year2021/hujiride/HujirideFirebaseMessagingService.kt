package huji.postpc.year2021.hujiride

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.*

class HujirideFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        val app = application as HujiRideApplication
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            app.db.setFCMToken(app.userDetails.clientUniqueID, token)
        }
    }
}