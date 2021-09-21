package huji.postpc.year2021.hujiride

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.MyRides.MyRidesAdapter
import huji.postpc.year2021.hujiride.Rides.Ride
import huji.postpc.year2021.hujiride.database.Ride as DbRide
import huji.postpc.year2021.hujiride.Rides.RidesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * dashboard shows rides that the user was signed to
 */
class Dashboard : Fragment() {

    private lateinit var aView: View
    private lateinit var adapter: MyRidesAdapter
    private lateinit var img: ImageView
    private lateinit var noRidesTxt: TextView
    private lateinit var titleTxt: TextView
    private lateinit var app : HujiRideApplication


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    private fun convertSingleDbRideToAppRide(dbRide: DbRide) : Ride{
        var src = "HUJI"
        var dest = "HUJI"
        if (dbRide.isDestinationHuji){
            src = dbRide.destName
        }else{
            dest = dbRide.destName
        }
        GlobalScope.launch(Dispatchers.IO) {
            val driver = app.db.findClient(dbRide.driverID.toString())


        }

        return Ride(src, dest, dbRide.time, dbRide.stops,
        dbRide.comments, driver.firstName,driver.lastName,
        driver.phoneNumber, dbRide.isDestinationHuji)
    }

    private fun convertDbRidesToAppRides(dbRides: ArrayList<DbRide>): ArrayList<Ride>{
        val list : ArrayList<Ride> = arrayListOf()
        for (dbRide in dbRides){
            val appRide = convertSingleDbRideToAppRide(dbRide)
            list.add(appRide)
        }
        return list
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        aView = inflater.inflate(R.layout.fragment_dashboard, container, false)
        app = HujiRideApplication.getInstance()
        img = aView.findViewById<ImageView>(R.id.no_rides_img)
        noRidesTxt = aView.findViewById<TextView>(R.id.no_near_rides_txt)
        titleTxt = aView.findViewById<TextView>(R.id.title_next_rides)
        adapter = MyRidesAdapter()
        val clientId = app.userDetails.clientUniqueID

        GlobalScope.launch(Dispatchers.IO) {

            val rides = app.db.getRidesOfClient(clientId)

            adapter.setClientsRides(convertDbRidesToAppRides(rides))
            adapter.notifyDataSetChanged()
        }



        if (adapter.itemCount == 0){
            noNearRidesCase()
        }else{
            thereAreRidesCase()
        }

        return aView
    }


    private fun noNearRidesCase(){

        img.visibility = View.VISIBLE
        noRidesTxt.visibility = View.VISIBLE
        titleTxt.visibility = View.INVISIBLE

    }


    private fun thereAreRidesCase(){

        img.visibility = View.INVISIBLE
        noRidesTxt.visibility = View.INVISIBLE
        titleTxt.visibility = View.VISIBLE



        val ridesRecycler: RecyclerView = aView!!.findViewById(R.id.my_rides_recycler)
        ridesRecycler.adapter = adapter
        ridesRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)

        adapter.onItemClickCallback = {ride: Ride ->
            vm.pressedRide.value = ride
            Navigation.findNavController(aView).navigate(R.id.action_dashboard_to_ridesDetails)
        }

    }


}