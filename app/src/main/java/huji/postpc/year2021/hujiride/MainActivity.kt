package huji.postpc.year2021.hujiride

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import huji.postpc.year2021.hujiride.Onboarding.OnBoardingVM
import huji.postpc.year2021.hujiride.Rides.Ride
import huji.postpc.year2021.hujiride.database.Database
import kotlinx.coroutines.*
import org.opencv.android.OpenCVLoader
import java.util.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val TAG = "MainActivity"

        if (OpenCVLoader.initDebug())

        {
            Log.d(TAG, "OpenCV Loaded")
        }
        else
        {
            Log.d(TAG, "OpenCV not Loaded")
        }

        setContentView(R.layout.activity_main)
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