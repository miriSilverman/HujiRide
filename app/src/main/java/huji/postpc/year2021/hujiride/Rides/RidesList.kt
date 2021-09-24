package huji.postpc.year2021.hujiride.Rides

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import huji.postpc.year2021.hujiride.R
import huji.postpc.year2021.hujiride.HujiRideApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import huji.postpc.year2021.hujiride.database.Ride



/**
 * list of the closest rides
 */
class RidesList : Fragment() {

    private lateinit var aView: View
    private var toHuji: Boolean = true
    private lateinit var img: ImageView
    private lateinit var noRidesTxt: TextView
    private lateinit var sortTIL: TextInputLayout
    private lateinit var filterTIL: TextInputLayout
    private lateinit var adapter: RidesAdapter
    private lateinit var vm: RidesViewModel
    private lateinit var app: HujiRideApplication
    private lateinit var progressBar: ProgressBar
    private lateinit var addRideBtn : Button
    private lateinit var sortACTV : AutoCompleteTextView
    private lateinit var filterACTV : AutoCompleteTextView
    private lateinit var ridesRecycler: RecyclerView





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun onResume() {
        super.onResume()
        val sortItems = resources.getStringArray(R.array.sorting_list)
        val sortingArrayAdapter = ArrayAdapter(requireContext(), R.layout.sort_item, sortItems)
        sortACTV.setAdapter(sortingArrayAdapter)

        val filteredItems = resources.getStringArray(R.array.filtering_list)
        val filterArrayAdapter = ArrayAdapter(requireContext(), R.layout.sort_item, filteredItems)
        filterACTV.setAdapter(filterArrayAdapter)

    }





    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        aView = inflater.inflate(R.layout.fragment_rides_list, container, false)
        app = HujiRideApplication.getInstance()

        findViews()

        addRideBtn.setOnClickListener {
            Navigation.findNavController(aView).navigate(R.id.action_ridesList_to_newRide2)
        }
        setVisibility(View.INVISIBLE, View.INVISIBLE, false, View.VISIBLE)


        adapter = RidesAdapter()

//        setDirection()

//        switchDirectionBtn.setOnClickListener {
//            toHuji = !toHuji
//            setDirection()
//        }


        vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
        val curGroup = vm.pressedGroup

        ridesRecycler.adapter = adapter
        ridesRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)


        adapter.onItemClickCallback = { ride: Ride ->
            vm.pressedRide.value = ride
            Navigation.findNavController(aView).navigate(R.id.action_ridesList_to_ridesDetails)
        }


        GlobalScope.launch(Dispatchers.IO) {
            val group = curGroup.value?.name
            var groupsName: String? = null
            if (group != null){
                groupsName = group.toString()
            }

            val dbRidesArr :List<Ride> = if (group == null){
                app.db.sortRidesAccordingToALocation(vm.latLng)
            }else{
                app.db.getRidesListOfGroup(groupsName)

            }

            adapter.setRidesList(dbRidesArr)



            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
                if (adapter.itemCount == 0) {
                    noNearRidesCase()
                } else {
                    thereAreRidesCase()
                }
            }

        }

        return aView
    }

    private fun findViews() {
        sortACTV = aView.findViewById(R.id.autoCompleteTextView2)
        filterACTV = aView.findViewById(R.id.autoCompleteFilter)
        addRideBtn = aView.findViewById(R.id.add_new_ride)
        img = aView.findViewById(R.id.no_rides_img)
        noRidesTxt = aView.findViewById(R.id.no_near_rides_txt)
        sortTIL = aView.findViewById(R.id.sort_as)
        filterTIL = aView.findViewById(R.id.filter)
        progressBar = aView.findViewById(R.id.rides_progress_bar)
        ridesRecycler = aView.findViewById(R.id.rides_list_recyclerView)
    }

    private fun setVisibility(oneDirection: Int, secondDirection: Int, btnState: Boolean, progressbarVis: Int){
        addRideBtn.isEnabled = btnState

        progressBar.visibility = progressbarVis

        noRidesTxt.visibility = oneDirection
        img.visibility = oneDirection

        ridesRecycler.visibility = secondDirection
        sortTIL.visibility = secondDirection
        filterTIL.visibility = secondDirection
//        switchDirectionBtn.visibility = secondDirection
//        srcDestImg.visibility = secondDirection

    }



//    private fun setDirection() {
//
//        if (toHuji) {
//
//            srcDestImg.setImageResource(R.drawable.resource_switch)
//
//        } else {
//            srcDestImg.setImageResource(R.drawable.switchfromhuji)
//
//
//        }
//    }

    private fun noNearRidesCase() {
        setVisibility(View.VISIBLE, View.INVISIBLE, true, View.INVISIBLE)
    }

    private fun thereAreRidesCase() {
        setVisibility(View.INVISIBLE, View.VISIBLE, true, View.INVISIBLE)
    }


}






