package huji.postpc.year2021.hujiride

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.shashank.sony.fancygifdialoglib.FancyGifDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ApplicationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application)


        val nav_view = findViewById<NavigationView>(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener(this)

        bottomNavigationView = findViewById(R.id.bottom_nav_view)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {

            super.onBackPressed()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_HowToUse ->
                howPressed()
            R.id.nav_settings ->
                settingPressed()
            //supportFragmentManager.beginTransaction().replace(R.id.onborading_nav_fragment_container, Settings()).commit()
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

    private fun howPressed() {
        FancyGifDialog.Builder(this)
            .setTitle("How to use?")
            .setMessage("This app allows you to connect to fellow students and drive with them to campus and back. \n\n At New Ride screen - which is reachable through the bottom menu - you can add a ride you are planning to drive or search for a ride as a passenger. \n\n Moreover, the GROUPS allows you to sign up to certain locations, and see all the rides to and from there. We recommend signing up to any group that is close to your home!")
            .setPositiveBtnBackground("#FF4081")
            .setPositiveBtnText("Got it").setNegativeBtnText("confused")
            //todo change gif
            .setGifResource(R.drawable.map) //Pass your Gif here
            .isCancellable(true)
            .OnPositiveClicked {
                Toast.makeText(this, "Good Luck!", Toast.LENGTH_SHORT).show()
            }.OnNegativeClicked {
                Toast.makeText(this, "Try 'New Ride' first, you will get it!", Toast.LENGTH_SHORT)
                    .show()

            }
            .build()
    }


    private fun bugPressed() {
        Log.d("bugs", "opened")
        val dialog = BugDialog()
        dialog.show(supportFragmentManager!!, "bug dialog")

    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun settingPressed() {

        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(this)

        val view = layoutInflater.inflate(R.layout.setting_alert_dialog, null)
        val switchAllNotifications: Switch = view.findViewById(R.id.all_notifications)
        val switchGroupsNotifications: Switch = view.findViewById(R.id.group_notifications)
        val app = HujiRideApplication.getInstance()

        //init
        switchAllNotifications.isChecked = app.userDetails.allNotifications
        switchGroupsNotifications.isChecked = app.userDetails.justGroupNotifications
        val clientId = app.userDetails.clientUniqueID


        switchAllNotifications.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (b) {
                app.userDetails.allNotifications = true
                app.userDetails.justGroupNotifications = false
                switchGroupsNotifications.isChecked = false
            } else {
                app.userDetails.allNotifications = false

            }
            app.saveNotificationsState(app.userDetails.allNotifications, app.userDetails.justGroupNotifications);
            GlobalScope.launch(Dispatchers.IO) {
                if (b){
                    app.db.registerClientToAllNotifications(clientId)
                }
            }

            //    Toast.makeText(this, "all: ${app.userDetails.allNotifications},\n groups: ${app.userDetails.justGroupNotifications}", Toast.LENGTH_SHORT).show()
        })


        switchGroupsNotifications.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->

            if (b) {
                app.userDetails.allNotifications = false
                app.userDetails.justGroupNotifications = true
                switchAllNotifications.isChecked = false
            } else {
                app.userDetails.justGroupNotifications = false
            }
            // Toast.makeText(this, "all: ${app.userDetails.allNotifications},\n groups: ${app.userDetails.justGroupNotifications}", Toast.LENGTH_SHORT).show()
            app.saveNotificationsState(
                app.userDetails.allNotifications,
                app.userDetails.justGroupNotifications
            );
            GlobalScope.launch(Dispatchers.IO) {
                if (b){
                    app.db.unregisterClientToAllNotifications(clientId)
                }
            }

        })

        builder.setPositiveButton("Done", DialogInterface.OnClickListener { d, m ->
            Toast.makeText(this, "changed!", Toast.LENGTH_SHORT).show()
        })
        builder.setView(view)
        dialog = builder.create()
        dialog.show()

    }

    private fun aboutPressed() {
        FancyGifDialog.Builder(this)
            .setTitle("About Us")
            .setMessage("This is the first version of HujiRide, The app that was built by Hebrew University's students for a SAFE, FAST and FUN way to get to campus and back home.\n\nOur Team was formed and guided in the course \"POST PC COMPUTING: HUMAN-CENTRIC MOBILE COMPUTING\".\n\n credits: Vector House Icon https://pngtree.com/freepng/vector-house-icon_4017483.html?share=1\n Pile of Papers, Map gifs by cliply.co")
            .setNegativeBtnText("Contact")
            .setPositiveBtnBackground("#FF4081")
            .setPositiveBtnText("OK")
            .setNegativeBtnBackground("#FFA9A7A8")
            //todo change gif
            .setGifResource(R.drawable.glassgif) //Pass your Gif here
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
            .build()
    }


    private fun sharePressed() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        val body = "Download this app"
        val sub = "http://play.google.com" //todo: change to real link
        intent.putExtra(Intent.EXTRA_TEXT, body)
        intent.putExtra(Intent.EXTRA_TEXT, sub)
        startActivity(Intent.createChooser(intent, "Share using"))
    }
}