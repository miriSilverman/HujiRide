package huji.postpc.year2021.hujiride

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import huji.postpc.year2021.hujiride.ToolBarFraments.About
import huji.postpc.year2021.hujiride.ToolBarFraments.BugsReport
import huji.postpc.year2021.hujiride.ToolBarFraments.Settings
import huji.postpc.year2021.hujiride.Rides.Ride

class ApplicationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawer: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application)


        val nav_view = findViewById<NavigationView>(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener(this)

//        bottomNavigationView = findViewById(R.id.bottom_nav_view)
//        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavHelper(supportFragmentManager))

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)

        drawer.addDrawerListener(toggle)
        toggle.syncState()


        doneOnboardingCase()

    }


    private fun doneOnboardingCase() {
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val navController = findNavController(R.id.onborading_nav_fragment_container)
        bottomNavView.setupWithNavController(navController)
    }


    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }
        else{

            super.onBackPressed()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_settings ->
                supportFragmentManager.beginTransaction().replace(R.id.onborading_nav_fragment_container, Settings()).commit()
            R.id.nav_about ->
                supportFragmentManager.beginTransaction().replace(R.id.onborading_nav_fragment_container, About()).commit()
            R.id.nav_bugs ->
                supportFragmentManager.beginTransaction().replace(R.id.onborading_nav_fragment_container, BugsReport()).commit()
            R.id.nav_share ->
                Toast.makeText(this, "share", Toast.LENGTH_SHORT).show()

        }

        drawer.closeDrawer(GravityCompat.START)


        return true
    }
}