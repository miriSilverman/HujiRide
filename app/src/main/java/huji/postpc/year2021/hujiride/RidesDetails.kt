package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.navigation.Navigation


/**
 * ride's details
 */
class RidesDetails : Fragment() {

    private var aView: View? = null


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
        aView?.findViewById<Button>(R.id.back_to_closest_rides)?.setOnClickListener {
            Navigation.findNavController(aView!!).navigate(R.id.action_ridesDetails_to_ridesList)
        }

        aView?.findViewById<Button>(R.id.contact_driver_btn)?.setOnClickListener {
            Navigation.findNavController(aView!!).navigate(R.id.action_ridesDetails_to_driversDetails)
        }
        return aView
    }


}