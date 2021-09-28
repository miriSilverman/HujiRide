package huji.postpc.year2021.hujiride

import android.app.AlertDialog
import android.content.DialogInterface
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
    private lateinit var addToMyRidesBtn: Button
    private lateinit var deleteFromMyRidesBtn: Button

    private lateinit var driverFullName: TextView
    private lateinit var driverPhone: TextView
    private lateinit var vm: RidesViewModel
    private lateinit var app: HujiRideApplication

    private fun findViews() {
        progressBar = aView.findViewById(R.id.drivers_progress_bar)
        addToMyRidesBtn = aView.findViewById(R.id.add_to_my_rides)
        deleteFromMyRidesBtn = aView.findViewById(R.id.delete_from_my_rides)

        driverFullName = aView.findViewById(R.id.full_name)
        driverPhone = aView.findViewById(R.id.phone_num)
    }

    private fun setVisibility(oneDirection: Int, secondDirection: Int, btnState: Boolean) {
        progressBar.visibility = oneDirection

        addToMyRidesBtn.isEnabled = btnState

        driverFullName.visibility = secondDirection
        driverPhone.visibility = secondDirection
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        aView = inflater.inflate(R.layout.fragment_drivers_details, container, false)
        app = HujiRideApplication.getInstance()
        findViews()
        setVisibility(View.VISIBLE, View.INVISIBLE, false)
        vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)

        decideAddOrDeleteBtn()


        val ride = vm.pressedRide.value
        if (ride != null) {

            GlobalScope.launch(Dispatchers.IO) {
                val driver = app.db.findClient(ride.driverID)
                withContext(Dispatchers.Main) {
                    setVisibility(View.INVISIBLE, View.VISIBLE, true)
                    if (driver != null) {
                        driverFullName.text = getString(R.string.driver_details_fullName, driver.firstName, driver.lastName)
                        driverPhone.text = driver.phoneNumber

                    }
                }

            }

        }


        return aView
    }

    private fun decideAddOrDeleteBtn() {
        addToMyRidesBtn.apply {
            setOnClickListener { addToRides() }
            visibility = if (vm.fromMyRides) View.GONE else View.VISIBLE
        }
        deleteFromMyRidesBtn.apply {
            setOnClickListener { deleteRides() }
            visibility = if (vm.fromMyRides) View.VISIBLE else View.GONE
        }
    }


    private fun addToRides() {
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


    private fun deleteRides() {
        AlertDialog.Builder(activity)
            .setTitle(R.string.deleteRideDialogTitle)
            .setMessage(R.string.deleteRideDialogTxt)
            .setIcon(R.drawable.ic_delete)
            .setCancelable(false)
            .setNegativeButton("no", null)
            .setPositiveButton("yes") { _: DialogInterface, _: Int ->

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
