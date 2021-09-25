package huji.postpc.year2021.hujiride

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import huji.postpc.year2021.hujiride.Rides.RidesViewModel
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem


/**
 * create or find new ride
 */
class SearchHome : Fragment() {

    private lateinit var srcDestImg: ImageView
    private lateinit var switchDirectionBtn: ImageButton
    private var toHuji: Boolean = true

    private lateinit var srcET: AutocompleteSupportFragment
    private lateinit var destET: AutocompleteSupportFragment
    private lateinit var destTextView: TextView
    private lateinit var srcTextView: TextView

    private lateinit var vm: RidesViewModel
    private var srcOrDestStr = ""
    private var latLng: LatLng = LatLng(0.0, 0.0)

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
        vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
        vm.pressedGroup.value = SearchGroupItem(null, false)
        vm.fromMyRides = false // todo: change to false

        findViews(view)


        autoCompletePlaces(srcET)
        autoCompletePlaces(destET)


        toHuji = vm.toHuji
        setDirection()

        switchDirectionBtn.setOnClickListener {
            setDetails()
            toHuji = !toHuji
            vm.toHuji = toHuji
            setDirection()
        }

        view.findViewById<TextView>(R.id.driver_btn).setOnClickListener {
            setDetails()
            Navigation.findNavController(view).navigate(R.id.action_search_home_to_newRide2)
        }

        view.findViewById<TextView>(R.id.trampist_btn).setOnClickListener {
            setDetails()
            Navigation.findNavController(view).navigate(R.id.action_search_home_to_ridesList)
        }


        return view
    }



    private fun autoCompletePlaces(autocompleteFragment: AutocompleteSupportFragment) {
        // places search bar
        Places.initialize(requireActivity(), "AIzaSyDTcekEAFGq-VG0MCPTNsYSwt9dKI8rIZA")
        val placesClient = Places.createClient(requireActivity())

        // Specify the types of place data to return.
        autocompleteFragment
            .setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
            .setCountry("IL")
            .setHint("חפש מוצא...") // TODO translated version?

        val TAG = "SEARCH"
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: ${place.name}, ${place.id}, ${place.latLng}")
                srcOrDestStr = place.name.toString()
                if (place.latLng != null){
                    latLng = place.latLng!!
                }
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
    }

    private fun findViews(view: View) {
        srcET = childFragmentManager.findFragmentById(R.id.place_autocomplete_fragment_src)
                as AutocompleteSupportFragment
        destET = childFragmentManager.findFragmentById(R.id.place_autocomplete_fragment_dest)
                as AutocompleteSupportFragment

        srcDestImg = view.findViewById(R.id.srcDestImg)
        switchDirectionBtn = view.findViewById(R.id.switchDirectionBtn)
        srcTextView = view.findViewById(R.id.src_huji)
        destTextView = view.findViewById(R.id.dest_huji)
    }


    private fun setDetails(){
        if (toHuji){
            syncVmAndET()
        }else{
            syncVmAndET()

        }
    }

    private fun syncVmAndET() {
        if (srcOrDestStr.isNotEmpty()) {
            vm.srcOrDest = srcOrDestStr
            vm.latLng = latLng
        } else {
            vm.srcOrDest = ""
        }
    }



    private fun setDirection() {
        if (toHuji) {
            designSwitchDirection(srcDestImg, destET, srcET, R.drawable.to_huji, destTextView, srcTextView)
        } else {
            designSwitchDirection(srcDestImg, srcET, destET, R.drawable.to_home, srcTextView, destTextView)
        }
        vm.toHuji = toHuji
    }



    private fun designSwitchDirection(img:ImageView, constWay: AutocompleteSupportFragment,
                                      notConstWay: AutocompleteSupportFragment, resOfImg: Int,
                                      tvVisible: TextView, tvInvisible: TextView) {
        img.setImageResource(resOfImg)
        notConstWay.view?.visibility = View.VISIBLE
        constWay.view?.visibility = View.INVISIBLE
        tvVisible.visibility = View.VISIBLE
        tvInvisible.visibility = View.INVISIBLE

        if (vm.srcOrDest != "") {
            notConstWay.setText(vm.srcOrDest)
        }
    }






}