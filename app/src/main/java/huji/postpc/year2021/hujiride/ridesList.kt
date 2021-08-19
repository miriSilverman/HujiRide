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
 * list of the closest rides
 */
class ridesList : Fragment() {

    private var aView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun onResume() {
        super.onResume()
        val sortItems = resources.getStringArray(R.array.sorting_list)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.sort_item, sortItems)
        aView?.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView2)?.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aView =  inflater.inflate(R.layout.fragment_rides_list, container, false)

        aView?.findViewById<Button>(R.id.add_new_ride)?.setOnClickListener {
            Navigation.findNavController(aView!!).navigate(R.id.action_ridesList_to_newRide2)
        }


        aView?.findViewById<Button>(R.id.ride_example)?.setOnClickListener {
            Navigation.findNavController(aView!!).navigate(R.id.action_ridesList_to_ridesDetails)
        }
        return aView
    }
}