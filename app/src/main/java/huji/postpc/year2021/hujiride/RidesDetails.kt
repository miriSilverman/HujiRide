package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation


/**
 * ride's details
 */
class RidesDetails : Fragment() {

    private var aView: View? = null

//    private var currentRide : Ride? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aView =  inflater.inflate(R.layout.fragment_rides_details, container, false)

        val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
        activity?.let { vm.pressedRide.observe(it, {
            ride ->

            aView?.findViewById<TextView>(R.id.source)?.setText(ride.src)
            aView?.findViewById<TextView>(R.id.destination)?.setText(ride.dest)
            aView?.findViewById<TextView>(R.id.time)?.setText(ride.time)
            var stops = ""
            for (s in ride.stops)
            {
                stops += "\n"+s
            }
            aView?.findViewById<TextView>(R.id.stops)?.setText(stops)

            var comments = ""
            for (s in ride.comments)
            {
                comments += "\n"+s
            }
            aView?.findViewById<TextView>(R.id.comments)?.setText(comments)


        }) }


        aView?.findViewById<Button>(R.id.back_to_closest_rides)?.setOnClickListener {
            Navigation.findNavController(aView!!).navigate(R.id.action_ridesDetails_to_ridesList)
        }

        aView?.findViewById<Button>(R.id.contact_driver_btn)?.setOnClickListener {
            Navigation.findNavController(aView!!).navigate(R.id.action_ridesDetails_to_driversDetails)
        }


        aView?.findViewById<Button>(R.id.back_to_groups)?.setOnClickListener {
            Navigation.findNavController(aView!!).navigate(R.id.action_ridesDetails_to_groups_home)
        }
        return aView
    }


}