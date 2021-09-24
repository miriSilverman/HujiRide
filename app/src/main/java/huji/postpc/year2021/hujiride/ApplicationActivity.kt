package huji.postpc.year2021.hujiride

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.shashank.sony.fancygifdialoglib.FancyGifDialog
import huji.postpc.year2021.hujiride.Groups.GroupsHome
import huji.postpc.year2021.hujiride.ToolBarFraments.About
import huji.postpc.year2021.hujiride.ToolBarFraments.BugsReport
import huji.postpc.year2021.hujiride.ToolBarFraments.Settings
import huji.postpc.year2021.hujiride.ToolBarFraments.Share
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener











class ApplicationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawer: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application)


        val nav_view = findViewById<NavigationView>(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener(this)

        bottomNavigationView = findViewById(R.id.bottom_nav_view)
//        bottomNavigationView.setOnNavigationItemSelectedListener(this)
//        bottomNavigationView.setOnClickListener {
//            println("###################### gggg")


//        }
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
                //settingPressed()
                 supportFragmentManager.beginTransaction().replace(R.id.onborading_nav_fragment_container, Settings()).commit()
            R.id.nav_about ->
                aboutPressed()
                //supportFragmentManager.beginTransaction().replace(R.id.onborading_nav_fragment_container, About()).commit()
            R.id.nav_bugs ->
                bugPressed()
                //supportFragmentManager.beginTransaction().replace(R.id.onborading_nav_fragment_container, BugsReport()).commit()
            R.id.nav_share ->
                sharePressed()
                //supportFragmentManager.beginTransaction().replace(R.id.onborading_nav_fragment_container, Share()).commit()
        }

        drawer.closeDrawer(GravityCompat.START)


        return true
    }

    private fun bugPressed()
    {
        Log.d("bugs","opened")
        val dialog = BugDialog()
        dialog.show(supportFragmentManager!!, "bug dialog")

    }

    private fun settingPressed()
    {

    }

    private fun aboutPressed()
    {
        FancyGifDialog.Builder(this)
            .setTitle("About Us")
            .setMessage("This is the first version of HujiRide, The app that was built by Hebrew University's students for a SAFE, FAST and FUN way to get to campus and back home.\n\nOur Team was formed and guided in the course \"POST PC COMPUTING: HUMAN-CENTRIC MOBILE COMPUTING\".\n")
            .setNegativeBtnText("Contact")
            .setPositiveBtnBackground("#FF4081")
            .setPositiveBtnText("OK")
            .setNegativeBtnBackground("#FFA9A7A8")
                //todo change gif
            .setGifResource(R.drawable.flight) //Pass your Gif here
            .isCancellable(true)
            .OnPositiveClicked {
                Toast.makeText(this, "Thanks For Reading", Toast.LENGTH_SHORT).show()
            }
            .OnNegativeClicked {
                val intent = Intent(
                    Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "hujiride@gmail.com", null
                    )

                )
                startActivity(Intent.createChooser(intent, "Mail from"))

            }
            .build()    }


    private fun sharePressed()
    {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        val body = "Download this app"
        val sub = "http://play.google.com" //todo: change to real link
        intent.putExtra(Intent.EXTRA_TEXT, body)
        intent.putExtra(Intent.EXTRA_TEXT, sub)
        startActivity(Intent.createChooser(intent, "Share using"))
    }
}