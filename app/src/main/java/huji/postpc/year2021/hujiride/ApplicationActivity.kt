package huji.postpc.year2021.hujiride

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class ApplicationActivity : AppCompatActivity() {

    private var drawer: DrawerLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application)




        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)

        drawer?.addDrawerListener(toggle)
        toggle.syncState()


        doneOnboardingCase()

    }


    private fun doneOnboardingCase() {
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val navController = findNavController(R.id.onborading_nav_fragment_container)
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