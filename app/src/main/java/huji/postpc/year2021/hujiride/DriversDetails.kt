package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_drivers_details, container, false)
        val app = HujiRideApplication.getInstance()

        view.findViewById<Button>(R.id.back_to_closest_rides).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_driversDetails_to_ridesList)
        }

        view.findViewById<Button>(R.id.back_to_groups).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_driversDetails_to_groups_home)
        }

        val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
        // todo: change to ID of ride somehow

        view.findViewById<Button>(R.id.add_to_my_rides).setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                vm.pressedRide.value?.let { it1 ->
                    app.db.addRideToClientsRides(
                        app.userDetails.clientUniqueID,
                        it1
                    )
                }
                withContext(Dispatchers.Main) {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_driversDetails_to_dashboard)

                }
            }
        }

        val ride = vm.pressedRide.value
        if (ride != null) {
            GlobalScope.launch(Dispatchers.IO) {
                val driver = app.db.findClient(ride.driverID)
                withContext(Dispatchers.Main) {
                    if (driver != null) {
                        view.findViewById<TextView>(R.id.first_name).text = driver.firstName
                        view.findViewById<TextView>(R.id.last_name).text = driver.lastName
                        view.findViewById<TextView>(R.id.phone_num).text = driver.phoneNumber

                    }
                }

            }

        }


        return view
    }

}