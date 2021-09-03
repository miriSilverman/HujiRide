package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.navigation.Navigation


/**
 * create new ride as driver
 */
class newRide : Fragment() {

    private var aView: View? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun onResume() {
        super.onResume()
        val stopsSortItems = resources.getStringArray(R.array.stops_list)
        val stopsArrayAdapter = ArrayAdapter(requireContext(), R.layout.sort_item, stopsSortItems)
        aView?.findViewById<AutoCompleteTextView>(R.id.autoCompleteStops)?.setAdapter(stopsArrayAdapter)


        val commentsSortItems = resources.getStringArray(R.array.comments_list)
        val commentsArrayAdapter = ArrayAdapter(requireContext(), R.layout.sort_item, commentsSortItems)
        aView?.findViewById<AutoCompleteTextView>(R.id.autoCompleteComments)?.setAdapter(commentsArrayAdapter)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        aView =  inflater.inflate(R.layout.fragment_new_ride, container, false)
        aView?.findViewById<ImageView>(R.id.done_btn)?.setOnClickListener {
            Navigation.findNavController(aView!!).navigate(R.id.action_newRide2_to_dashboard)
        }
        return aView
    }
}