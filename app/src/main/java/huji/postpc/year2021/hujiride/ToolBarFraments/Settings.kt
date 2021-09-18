package huji.postpc.year2021.hujiride.ToolBarFraments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import huji.postpc.year2021.hujiride.R
import huji.postpc.year2021.hujiride.HujiRideApplication

class Settings: Fragment() {

    private lateinit var switchAllNotifications : Switch
    private lateinit var switchGroupsNotifications : Switch

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.settings, container, false)
        switchAllNotifications = view.findViewById(R.id.all_notifications)
        switchGroupsNotifications = view.findViewById(R.id.group_notifications)
        val app = HujiRideApplication.getInstance()

        //init
        switchAllNotifications.isChecked = app.userDetails.allNotifications
        switchGroupsNotifications.isChecked = app.userDetails.justGroupNotifications


        switchAllNotifications.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->

            if (b){
                app.userDetails.allNotifications = true
                app.userDetails.justGroupNotifications = false
                switchGroupsNotifications.isChecked = false
            }else{
                app.userDetails.allNotifications = false
            }

            Toast.makeText(activity, "all: ${app.userDetails.allNotifications},\n groups: ${app.userDetails.justGroupNotifications}", Toast.LENGTH_SHORT).show()
        })


        switchGroupsNotifications.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->

            if (b){
                app.userDetails.allNotifications = false
                app.userDetails.justGroupNotifications = true
                switchAllNotifications.isChecked = false
            }else{
                app.userDetails.justGroupNotifications = false
            }
            Toast.makeText(activity, "all: ${app.userDetails.allNotifications},\n groups: ${app.userDetails.justGroupNotifications}", Toast.LENGTH_SHORT).show()

        })
        return view
    }


}
