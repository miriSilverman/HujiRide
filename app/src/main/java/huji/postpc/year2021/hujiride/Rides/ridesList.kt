package huji.postpc.year2021.hujiride.Rides

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.R
import huji.postpc.year2021.hujiride.RidesPerGroups
import huji.postpc.year2021.hujiride.HujiRideApplication


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


        val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
        val curGroup = vm.pressedGroup
        val ridesPerGroup = HujiRideApplication.getInstance().ridesPerGroup

        val rides = ridesPerGroup.map[curGroup.value?.name]

        val adapter = RidesAdapter()
        if (rides != null) {
            adapter.setRidesList(rides)
        }

        if (aView != null)
        {
            val ridesRecycler: RecyclerView = aView!!.findViewById(R.id.rides_list_recyclerView)
            ridesRecycler.adapter = adapter
            ridesRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)


            adapter.onItemClickCallback = {ride: Ride ->
                vm.pressedRide.value = ride
                Navigation.findNavController(aView!!).navigate(R.id.action_ridesList_to_ridesDetails)
            }

        }


        return aView
    }
}



//        val rides: List<Ride> = arrayListOf(
//            Ride("Gilo", "huji", "15:03", arrayListOf("gas station", "babies"), arrayListOf("no smoking", "just women"), "miri", "silverman", "0543697578"),
//            Ride("huji", "Malcha", "14:03", arrayListOf("gas station"), arrayListOf("put masks","no smoking"),"yair", "Gueta", "0547584545"),
//            Ride("Ramot", "huji", "15:13", arrayListOf("gas station"), arrayListOf("no smoking"), "Amit", "Cohen", "0545656655"),
//            Ride("Talpiyot", "huji", "05:23", arrayListOf("gas station"), arrayListOf("no smoking", "vaccinated" ), "Shir", "Levi", "0536542897"),
//            Ride("huji", "Bakaa", "04:03", arrayListOf("gas station"), arrayListOf("no smoking"), "Dafna", "Golan", "0547587899"),
//            Ride("Gilo", "Beit hakerem", "15:03", arrayListOf("gas station"), arrayListOf("no smoking"), "Kobi", "Israeli", "0563698585")
//        )