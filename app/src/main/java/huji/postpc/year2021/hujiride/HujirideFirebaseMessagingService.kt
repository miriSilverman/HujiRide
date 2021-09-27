package huji.postpc.year2021.hujiride

import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class HujirideFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        val app = application as HujiRideApplication
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            app.db.setFCMToken(app.userDetails.clientUniqueID, token)
        }
    }
}