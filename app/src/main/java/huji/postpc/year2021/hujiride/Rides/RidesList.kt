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
import huji.postpc.year2021.hujiride.GroupsRides
import huji.postpc.year2021.hujiride.HujiRideApplication
import huji.postpc.year2021.hujiride.Dashboard
import huji.postpc.year2021.hujiride.MyRides.MyRidesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * list of the closest rides
 */
class RidesList : Fragment() {

    private lateinit var aView: View
    private lateinit var srcDestImg: ImageView
    private lateinit var switchDirectionBtn: Button
    private var toHuji: Boolean = true
    private lateinit var img: ImageView
    private lateinit var noRidesTxt: TextView
    private lateinit var sortAs: TextInputLayout
    private lateinit var adapter: RidesAdapter
    private lateinit var vm: RidesViewModel
    private lateinit var app: HujiRideApplication


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun onResume() {
        super.onResume()
        val sortItems = resources.getStringArray(R.array.sorting_list)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.sort_item, sortItems)
        aView.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView2)
            ?.setAdapter(arrayAdapter)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        aView = inflater.inflate(R.layout.fragment_rides_list, container, false)
        app = HujiRideApplication.getInstance()
        aView.findViewById<Button>(R.id.add_new_ride)?.setOnClickListener {
            Navigation.findNavController(aView).navigate(R.id.action_ridesList_to_newRide2)
        }

        img = aView.findViewById<ImageView>(R.id.no_rides_img)
        noRidesTxt = aView.findViewById<TextView>(R.id.no_near_rides_txt)
        sortAs = aView.findViewById<TextInputLayout>(R.id.sort_as)
        srcDestImg = aView.findViewById(R.id.srcDestImg)
        switchDirectionBtn = aView.findViewById(R.id.switchDirectionBtn)


        val adapter = RidesAdapter()

        setDirection()

        switchDirectionBtn.setOnClickListener {
            toHuji = !toHuji
            setDirection()
        }


        vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
        val curGroup = vm.pressedGroup

//        val ridesPerGroup = HujiRideApplication.getInstance().ridesPerGroup
//        val rides: GroupsRides? = ridesPerGroup.map[curGroup.value?.name]
//        if (rides != null) {
//            adapter.setRidesList(rides.ridesList)
//        }

        val ridesRecycler: RecyclerView = aView.findViewById(R.id.rides_list_recyclerView)
        ridesRecycler.adapter = adapter
        ridesRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)


        adapter.onItemClickCallback = { ride: Ride ->
            vm.pressedRide.value = ride
            Navigation.findNavController(aView).navigate(R.id.action_ridesList_to_ridesDetails)
        }

        GlobalScope.launch(Dispatchers.IO) {
            val dbRidesArr = app.db.getRidesListOfGroup(curGroup.value?.name.toString())

            val appRidesArr = convertDbRidesToAppRides(dbRidesArr)
            adapter.setRidesList(appRidesArr)
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


    suspend fun convertSingleDbRideToAppRide(dbRide: huji.postpc.year2021.hujiride.database.Ride) : Ride?{
        var src = "HUJI"
        var dest = "HUJI"
        if (dbRide.isDestinationHuji){
            src = dbRide.destName
        }else{
            dest = dbRide.destName
        }
        val driver = app.db.findClient(dbRide.driverID.toString())

        if (driver != null) {
            return Ride(src, dest, dbRide.time, dbRide.stops,
                dbRide.comments, driver.firstName,driver.lastName,
                driver.phoneNumber, dbRide.isDestinationHuji)
        }
        return null
    }



    suspend fun convertDbRidesToAppRides(dbRides: ArrayList<huji.postpc.year2021.hujiride.database.Ride>): ArrayList<Ride>{
        val list : ArrayList<Ride> = arrayListOf()
        for (dbRide in dbRides){
            val appRide = convertSingleDbRideToAppRide(dbRide)
            if (appRide != null) {
                list.add(appRide)
            }
        }
        return list
    }

    private fun setDirection() {

        //todo: show only toHuji in right direction
        if (toHuji) {

            srcDestImg.setImageResource(R.drawable.resource_switch)

        } else {
            srcDestImg.setImageResource(R.drawable.switchfromhuji)


        }
    }

    private fun noNearRidesCase() {

        img.visibility = View.VISIBLE
        noRidesTxt.visibility = View.VISIBLE
        sortAs.visibility = View.INVISIBLE
        srcDestImg.visibility = View.INVISIBLE
        switchDirectionBtn.visibility = View.INVISIBLE


    }

    private fun thereAreRidesCase() {

        img.visibility = View.INVISIBLE
        noRidesTxt.visibility = View.INVISIBLE
        sortAs.visibility = View.VISIBLE
        srcDestImg.visibility = View.VISIBLE
        switchDirectionBtn.visibility = View.VISIBLE


    }


}






