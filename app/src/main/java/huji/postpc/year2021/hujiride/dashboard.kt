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
import huji.postpc.year2021.hujiride.Rides.Ride
import huji.postpc.year2021.hujiride.Rides.RidesAdapter
import huji.postpc.year2021.hujiride.Rides.RidesViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [dashboard.newInstance] factory method to
 * create an instance of this fragment.
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
        // Inflate the layout for this fragment
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