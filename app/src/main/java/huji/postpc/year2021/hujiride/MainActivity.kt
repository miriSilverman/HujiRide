package huji.postpc.year2021.hujiride

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.edit
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import huji.postpc.year2021.hujiride.Onboarding.OnboradingActivity
import huji.postpc.year2021.hujiride.database.Client
import huji.postpc.year2021.hujiride.database.Ride
import huji.postpc.year2021.hujiride.database.Database
import huji.postpc.year2021.hujiride.database.createTimeStamp
import kotlinx.coroutines.*
import org.opencv.android.OpenCVLoader
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var app: HujiRideApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app = application as HujiRideApplication

        val TAG = "MainActivity"


        if (OpenCVLoader.initDebug()) {
            Log.d(TAG, "OpenCV Loaded")
        } else {
            Log.d(TAG, "OpenCV not Loaded")
        }


        val userUniqueID = getUniqueID()
        app.userDetails.clientUniqueID = userUniqueID

        GlobalScope.launch(Dispatchers.IO) {
            val client = app.db.findClient(userUniqueID)
            if (client == null) {
                clientNotExistCase()
            } else if (!client.isAuth) {
                clientNotExistCase()
            } else {
                clientExistsCase(client, userUniqueID)
            }
        }
//        TESTS()
    }

    private fun TESTS() {
        val db = Database()
        val cRide = Ride(
            time = "PEND",
            timeStamp = createTimeStamp("24-9-2021 21:50")
        )

        val bRide = Ride(
            time = "FUTURE",
            timeStamp = createTimeStamp("25-9-2021 16:00")
        )

        val a = Ride(
            time = "ENDED",
            timeStamp = createTimeStamp("24-9-2021 20:09")
        )
        GlobalScope.launch {
            val id = app.userDetails.clientUniqueID
//            val ids = mutableListOf(
//                db.addRide(cRide, "YAIR TEST", null),
//                db.addRide(a, "YAIR TEST", "0"),
//                db.addRide(bRide, "YAIR TEST", "0")
//            )
            val r = db.getClientCreatedRides(id)
            p("\nID: $id\nRides: $r\n")
        }
    }

    fun p(m: String) {
        Log.d("TESTS", m)
    }

    private fun getUniqueID(): String {
        val sp = getSharedPreferences("huji.rides.unique.id.sp", Context.MODE_PRIVATE)
        val spKey = "huji.rides.yair.unique.id.client"
        val uniqueID = sp.getString(spKey, null)
        if (uniqueID != null) {
            return uniqueID
        }
        val newUniqueID = UUID.randomUUID().toString()
        sp.edit {
            putString(spKey, newUniqueID)
        }
        return newUniqueID
    }

    private fun clientNotExistCase() {
        startActivity(Intent(this, OnboradingActivity::class.java))
        finish()
    }

    private fun clientExistsCase(client: Client, userUniqueID: String) {
        app.setClientData(client.firstName, client.lastName, client.phoneNumber, userUniqueID)
        startActivity(Intent(this, ApplicationActivity::class.java))
        finish()
    }

}