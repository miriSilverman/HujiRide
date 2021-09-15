package huji.postpc.year2021.hujiride

import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import huji.postpc.year2021.hujiride.ToolBarFraments.About
import huji.postpc.year2021.hujiride.ToolBarFraments.BugsReport
import huji.postpc.year2021.hujiride.ToolBarFraments.Settings
import huji.postpc.year2021.hujiride.Groups.GroupsHome

class BottomNavHelper: BottomNavigationView.OnNavigationItemSelectedListener {

    private var supportFragmentManager: FragmentManager? = null

    constructor(fragmentManager: FragmentManager){
        supportFragmentManager = fragmentManager

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.groups_home ->
                supportFragmentManager?.beginTransaction()?.replace(R.id.onborading_nav_fragment_container, GroupsHome())?.commit()
            R.id.dashboard ->
                supportFragmentManager?.beginTransaction()?.replace(R.id.onborading_nav_fragment_container, Dashboard())?.commit()
            R.id.search_home ->
                supportFragmentManager?.beginTransaction()?.replace(R.id.onborading_nav_fragment_container, SearchHome())?.commit()

        }

        return true
    }
}