package huji.postpc.year2021.hujiride.Rides

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import huji.postpc.year2021.hujiride.R


/**
 * ride's details
 */
class RidesDetails : Fragment() {

    private lateinit var aView: View



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        aView =  inflater.inflate(R.layout.fragment_rides_details, container, false)

        val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
        activity?.let { vm.pressedRide.observe(it, {
            ride ->

            aView.findViewById<TextView>(R.id.source)?.text = ride.src
            aView.findViewById<TextView>(R.id.destination)?.text = ride.dest
            aView.findViewById<TextView>(R.id.time)?.text = ride.time
            var stops = ""
            for (s in ride.stops)
            {
                stops += "\n"+s
            }
            aView.findViewById<TextView>(R.id.stops)?.text = stops

            var comments = ""
            for (s in ride.comments)
            {
                comments += "\n"+s
            }
            aView.findViewById<TextView>(R.id.comments)?.text = comments


        }) }


        aView.findViewById<Button>(R.id.back_to_closest_rides)?.setOnClickListener {
            Navigation.findNavController(aView).navigate(R.id.action_ridesDetails_to_ridesList)
        }

        aView.findViewById<Button>(R.id.contact_driver_btn)?.setOnClickListener {
            Navigation.findNavController(aView).navigate(R.id.action_ridesDetails_to_driversDetails)
        }


        aView.findViewById<Button>(R.id.back_to_groups)?.setOnClickListener {
            Navigation.findNavController(aView).navigate(R.id.action_ridesDetails_to_groups_home)
        }
        return aView
    }


}