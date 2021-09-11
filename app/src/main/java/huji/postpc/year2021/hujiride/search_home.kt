package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import huji.postpc.year2021.hujiride.Rides.RidesViewModel
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem


/**
 * create or find new ride
 */
class search_home : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_search_home, container, false)
        val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
        vm.pressedGroup.value = SearchGroupItem("all", false)

        view.findViewById<TextView>(R.id.driver_btn).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_search_home_to_newRide2)
        }

        view.findViewById<TextView>(R.id.trampist_btn).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_search_home_to_ridesList)
        }
        return view
    }


}