package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation


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
        return view
    }

}