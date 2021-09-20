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
import huji.postpc.year2021.hujiride.Onboarding.OnboradingActivity
import huji.postpc.year2021.hujiride.Rides.Ride
import huji.postpc.year2021.hujiride.database.Client
import huji.postpc.year2021.hujiride.database.Database
import kotlinx.coroutines.*
import org.opencv.android.OpenCVLoader
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var app: HujiRideApplication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val TAG = "MainActivity"

//        TESTS()

        if (OpenCVLoader.initDebug()) {
            Log.d(TAG, "OpenCV Loaded")
        } else {
            Log.d(TAG, "OpenCV not Loaded")
        }

        app = application as HujiRideApplication
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

    }

    private fun TESTS() {
        val db = Database()
        val cRide = Ride(
            src="TEST",
            dest="TEST",
            drivers_first_name = "YAIR",
            time = "",
            stops = arrayListOf(),
            comments = arrayListOf(),
            drivers_last_name = "LAST",
            drivers_phone_number = "000123",
            toHuji = true
        )

        GlobalScope.launch {
            val id = db.addRide(cRide, "TEST1", "0")
            println("DONE ######$id")
        }
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

private fun placesSearch() {
    // places search bar
    Places.initialize(this, "AIzaSyDTcekEAFGq-VG0MCPTNsYSwt9dKI8rIZA")
    val placesClient = Places.createClient(this)

    // Initialize the AutocompleteSupportFragment.
//        val autocompleteFragment =
//            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
//                    as AutocompleteSupportFragment

    val autocompleteFragment =
        supportFragmentManager.findFragmentById(-1)
                as AutocompleteSupportFragment
    // Specify the types of place data to return.
    autocompleteFragment
        .setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
        .setCountry("IL")
        .setHint("חפש מוצא...") // TODO translated version?

    val TAG = "SEARCH"
    // Set up a PlaceSelectionListener to handle the response.
    autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
        override fun onPlaceSelected(place: Place) {
            // TODO: Get info about the selected place.
            Log.i(TAG, "Place: ${place.name}, ${place.id}, ${place.latLng}")
        }

        override fun onError(status: Status) {
            // TODO: Handle the error.
            Log.i(TAG, "An error occurred: $status")
        }
    })
}

}