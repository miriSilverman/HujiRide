package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation


/**
 * A simple [Fragment] subclass.
 * Use the [ridesList.newInstance] factory method to
 * create an instance of this fragment.
 */
class ridesList : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_rides_list, container, false)
        view.findViewById<Button>(R.id.add_new_ride).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_ridesList_to_newRide2)
        }


        view.findViewById<Button>(R.id.ride_example).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_ridesList_to_ridesDetails)
        }
        return view
    }
}