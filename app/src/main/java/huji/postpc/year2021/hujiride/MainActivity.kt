package huji.postpc.year2021.hujiride

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
import java.util.*

class MainActivity : AppCompatActivity() {

    private val SHARED = "shared"
    private val DONE_ONBOARDING = "done onboarding"
    private val FIRST_NAME = "first name"
    private val LAST_NAME = "last name"
    private val PHONE_NUMBER = "phone num"
    val db : Database = Database()

    private var drawer: DrawerLayout? = null

    026266000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)







        setContentView(R.layout.activity_main)

        // places search bar
        Places.initialize(this, "AIzaSyDTcekEAFGq-VG0MCPTNsYSwt9dKI8rIZA")
        val placesClient = Places.createClient(this)

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment
    println("HIHIHIHI")
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


        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar,
        R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)

        drawer?.addDrawerListener(toggle)
        toggle.syncState()

        val pb = findViewById<ProgressBar>(R.id.Yair)

         //DB things
        GlobalScope.launch(Dispatchers.IO) {
            println("#########################")

//            pb.visibility  = View.VISIBLE
            db.newClient("TEST2", "TEST1", "000", "TEST1")
            db.findClient("TEST2")?.firstName
            println("#########################")
            withContext(Dispatchers.Main) {
                pb.visibility = View.GONE
            }

            db.newRide(Ride("TEST", "TEST", "NOW", arrayListOf(), arrayListOf(),
                "Stephan", "TEST", "87567", UUID.randomUUID()),
                "TEST1")
            db.registerClientToGroup("TEST1", 11)
            db.registerClientToGroup("TEST1", 152)
            db.registerClientToGroup("TEST1", 30)
//            pb.visibility = View.GONE

            // not show loading
        }



        val sp: SharedPreferences = this.getSharedPreferences(SHARED, Context.MODE_PRIVATE)

        val doneOnboarding = sp.getBoolean(DONE_ONBOARDING, false)

        if (doneOnboarding) {
            doneOnboardingCase()
        }
            else {
            onboardingCase(sp)
        }
    }



    private fun onboardingCase(sp: SharedPreferences) {

        val sharedVM = ViewModelProvider(this).get(OnBoardingVM::class.java)
        val userDetails = HujiRideApplication.getInstance().userDetails

        sharedVM.doneArr.observe(this, Observer { arr ->

            var num = 0
            for (task in arr) {
                if (task) num++
            }
            findViewById<ProgressBar>(R.id.progressBar).progress = num
        })


        sharedVM.doneOnBoard.observe(this, Observer { b ->
            if (b) {
                doneOnboardingCase()
                val editor: SharedPreferences.Editor = sp.edit()
                editor.putBoolean(DONE_ONBOARDING, true)
                editor.putString(FIRST_NAME, userDetails.userFirstName)
                editor.putString(LAST_NAME, userDetails.userLastName)
                editor.putString(PHONE_NUMBER, userDetails.userPhoneNumber)
                editor.apply()
            }
        })
    }



    private fun doneOnboardingCase() {
        setContentView(R.layout.home)
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavView.setupWithNavController(navController)
    }


    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)){
            drawer!!.closeDrawer(GravityCompat.START)
        }
        else{

            super.onBackPressed()
        }

    }

}