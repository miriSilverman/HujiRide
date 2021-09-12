package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.MyRides.MyRidesAdapter
import huji.postpc.year2021.hujiride.Rides.Ride
import huji.postpc.year2021.hujiride.Rides.RidesViewModel


/**
 * dashboard shows rides that the user was signed to
 */
class dashboard : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val aView = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val adapter = MyRidesAdapter()

        val ridesRecycler: RecyclerView = aView!!.findViewById(R.id.my_rides_recycler)
        ridesRecycler.adapter = adapter
        ridesRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)

        adapter.onItemClickCallback = {ride: Ride ->
            vm.pressedRide.value = ride
            Navigation.findNavController(aView!!).navigate(R.id.action_dashboard_to_ridesDetails)
        }



        return aView
    }


}