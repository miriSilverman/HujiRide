package huji.postpc.year2021.hujiride

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
import huji.postpc.year2021.hujiride.MyRides.MyRidesAdapter
import huji.postpc.year2021.hujiride.database.Ride
import huji.postpc.year2021.hujiride.Rides.RidesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * dashboard shows rides that the user was signed to
 */
class Dashboard : Fragment() {

    private lateinit var aView: View
    private lateinit var adapter: MyRidesAdapter
    private lateinit var img: ImageView
    private lateinit var noRidesTxt: TextView
    private lateinit var titleTxt: TextView
    private lateinit var app: HujiRideApplication
    private lateinit var progressBar: ProgressBar
    private lateinit var ridesRecycler: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }



    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        aView = inflater.inflate(R.layout.fragment_dashboard, container, false)
        findViews()
        setVisibility(View.INVISIBLE, View.INVISIBLE, View.VISIBLE)
        app = HujiRideApplication.getInstance()
        adapter = MyRidesAdapter()
        val clientId = app.userDetails.clientUniqueID

        GlobalScope.launch(Dispatchers.IO) {

            val rides = app.db.getRidesOfClient(clientId)

            adapter.setClientsRides(rides)
            adapter.notifyDataSetChanged()
            withContext(Dispatchers.Main) {
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
        img = aView.findViewById(R.id.no_rides_img)
        noRidesTxt = aView.findViewById(R.id.no_near_rides_txt)
        titleTxt = aView.findViewById(R.id.title_next_rides)

        progressBar = aView.findViewById(R.id.rides_progress_bar)
        ridesRecycler = aView.findViewById(R.id.my_rides_recycler)
    }

    private fun setVisibility(oneDirection: Int, secondDirection: Int, progressbarVis: Int) {
        noRidesTxt.visibility = oneDirection
        img.visibility = oneDirection

        ridesRecycler.visibility = secondDirection

        progressBar.visibility = progressbarVis
    }



    private fun noNearRidesCase() {
        setVisibility(View.VISIBLE, View.INVISIBLE, View.INVISIBLE)
    }


    private fun thereAreRidesCase() {
        setVisibility(View.INVISIBLE, View.VISIBLE, View.INVISIBLE)

        val ridesRecycler: RecyclerView = aView.findViewById(R.id.my_rides_recycler)
        ridesRecycler.adapter = adapter
        ridesRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)

        adapter.onItemClickCallback = { ride: Ride ->
            vm.pressedRide.value = ride
            Navigation.findNavController(aView).navigate(R.id.action_dashboard_to_ridesDetails)
        }

    }


}