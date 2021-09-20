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


/**
 * Driver's details
 */
class DriversDetails : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_drivers_details, container, false)
        view.findViewById<Button>(R.id.back_to_closest_rides).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_driversDetails_to_ridesList)
        }

        view.findViewById<Button>(R.id.back_to_groups).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_driversDetails_to_groups_home)
        }

        val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
        activity?.let { vm.pressedRide.observe(it, {
            ride ->
            view.findViewById<TextView>(R.id.first_name).text = ride.drivers_first_name
            view.findViewById<TextView>(R.id.last_name).text = ride.drivers_last_name
            view.findViewById<TextView>(R.id.phone_num).text = ride.drivers_phone_number
        }) }


        view.findViewById<Button>(R.id.add_to_my_rides).setOnClickListener {

        val app = HujiRideApplication.getInstance()
//            app.myRides.addRide(vm.pressedRide.value!!)
            Navigation.findNavController(view).navigate(R.id.action_driversDetails_to_dashboard)

        }




        return view
    }

}