package huji.postpc.year2021.hujiride

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import huji.postpc.year2021.hujiride.Rides.RidesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * Driver's details
 */
class DriversDetails : Fragment() {
    private lateinit var aView: View
    private lateinit var progressBar: ProgressBar
    private lateinit var backToRidesBtn: Button
    private lateinit var backToGroupsBtn: Button
    private lateinit var addToMyRidesBtn: Button

    private lateinit var driverFirstName: TextView
    private lateinit var driverLastName: TextView
    private lateinit var driverPhone: TextView
    private lateinit var vm: RidesViewModel
    private lateinit var app: HujiRideApplication

    private fun findViews() {
        progressBar = aView.findViewById(R.id.drivers_progress_bar)
        backToRidesBtn = aView.findViewById(R.id.back_to_closest_rides)
        backToGroupsBtn = aView.findViewById(R.id.back_to_groups)
        addToMyRidesBtn = aView.findViewById(R.id.add_to_my_rides)

        driverFirstName = aView.findViewById(R.id.first_name)
        driverLastName = aView.findViewById(R.id.last_name)
        driverPhone = aView.findViewById(R.id.phone_num)
    }

    private fun setVisibility(oneDirection: Int, secondDirection: Int, btnState: Boolean) {
        progressBar.visibility = oneDirection

        backToGroupsBtn.isEnabled = btnState
        backToRidesBtn.isEnabled = btnState
        addToMyRidesBtn.isEnabled = btnState

        driverFirstName.visibility = secondDirection
        driverLastName.visibility = secondDirection
        driverPhone.visibility = secondDirection


    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aView = inflater.inflate(R.layout.fragment_drivers_details, container, false)
        app = HujiRideApplication.getInstance()
        findViews()
        setVisibility(View.VISIBLE, View.INVISIBLE, false)
        vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)

        backToRidesBtn.setOnClickListener {
            Navigation.findNavController(aView).navigate(R.id.action_driversDetails_to_ridesList)
        }

        backToGroupsBtn.setOnClickListener {
            Navigation.findNavController(aView).navigate(R.id.action_driversDetails_to_groups_home)
        }

        decideAddOrDeleteBtn()


        val ride = vm.pressedRide.value
        if (ride != null) {
            GlobalScope.launch(Dispatchers.IO) {
                val driver = app.db.findClient(ride.driverID)
                println("##### $driver")
                withContext(Dispatchers.Main) {
                    setVisibility(View.INVISIBLE, View.VISIBLE, true)
                    if (driver != null) {
                        driverFirstName.text = driver.firstName
                        driverLastName.text = driver.lastName
                        driverPhone.text = driver.phoneNumber

                    }
                }

            }

        }


        return aView
    }

    private fun decideAddOrDeleteBtn() {
        if (vm.fromMyRides) {
            addToMyRidesBtn.text = "delete from my rides"
            addToMyRidesBtn.setBackgroundColor(Color.RED)

        } else {
            addToMyRidesBtn.text = "Add to my rides"
            addToMyRidesBtn.setBackgroundColor(Color.GREEN)
        }

        addToMyRidesBtn.setOnClickListener {
            if (!vm.fromMyRides){
                addToRides()
            }else{
                deleteRides()
            }

        }

    }



    private fun addToRides(){
        GlobalScope.launch(Dispatchers.IO) {
            vm.pressedRide.value?.let { it1 ->
                app.db.addRideToClientsRides(
                    app.userDetails.clientUniqueID,
                    it1
                )
            }
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, R.string.addRideDialogToast, Toast.LENGTH_SHORT).show()

                Navigation.findNavController(aView)
                    .navigate(R.id.action_driversDetails_to_dashboard)
            }
        }
    }


    private fun deleteRides(){


        AlertDialog.Builder(activity)
            .setTitle(R.string.deleteRideDialogTitle)
            .setMessage(R.string.deleteRideDialogTxt)
            .setIcon(R.drawable.ic_delete)
            .setCancelable(false)
            .setNegativeButton(android.R.string.no, null)
            .setPositiveButton(android.R.string.yes) { _: DialogInterface, _: Int ->

                GlobalScope.launch(Dispatchers.IO) {
                    vm.pressedRide.value?.let { it1 ->
                        app.db.deleteRideFromClientsRides(
                            app.userDetails.clientUniqueID,
                            it1
                        )
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, R.string.deleteRideDialogToast, Toast.LENGTH_SHORT)
                            .show()
                        Navigation.findNavController(aView)
                            .navigate(R.id.action_driversDetails_to_dashboard)
                    }
                }


            }
            .create().show()
    }

}