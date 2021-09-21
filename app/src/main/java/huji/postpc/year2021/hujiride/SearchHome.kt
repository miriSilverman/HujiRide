package huji.postpc.year2021.hujiride

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import huji.postpc.year2021.hujiride.Rides.RidesViewModel
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem


/**
 * create or find new ride
 */
class SearchHome : Fragment() {

    private lateinit var srcDestImg: ImageView
    private lateinit var switchDirectionBtn: Button
    private var toHuji: Boolean = true

    private lateinit var srcET: EditText
    private lateinit var destET: EditText
    private lateinit var vm: RidesViewModel
    private var srcOrDestStr = ""

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
//        vm.pressedGroup.value = SearchGroupItem("all", false)
        vm.pressedGroup.value = SearchGroupItem(null, false)

        findViews(view)

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










        // places search bar
        Places.initialize(requireActivity(), "AIzaSyDTcekEAFGq-VG0MCPTNsYSwt9dKI8rIZA")
        val placesClient = Places.createClient(requireActivity())

        // Initialize the AutocompleteSupportFragment.
//        val autocompleteFragment =
//            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
//                    as AutocompleteSupportFragment

        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.place_autocomplete_fragment)
                    as AutocompleteSupportFragment
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
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })


        return view
    }

    private fun findViews(view: View) {
        srcET = view.findViewById(R.id.source_edit_text)
        destET = view.findViewById(R.id.dest_edit_text)
        srcDestImg = view.findViewById(R.id.srcDestImg)
        switchDirectionBtn = view.findViewById(R.id.switchDirectionBtn)
    }


    private fun setDetails(){
        if (toHuji){
            syncVmAndET(srcET)
        }else{
            syncVmAndET(destET)

        }
    }

    private fun syncVmAndET(editText: EditText) {
        if (srcOrDestStr.isNotEmpty()) {
//        if (editText.text?.isEmpty() != true) {
//            vm.srcOrDest = editText.text.toString()
            vm.srcOrDest = srcOrDestStr
        } else {
            vm.srcOrDest = ""
        }
    }



    private fun setDirection() {
        if (toHuji) {
            designSwitchDirection(srcDestImg, destET, srcET, R.drawable.resource_switch)

        } else {
            designSwitchDirection(srcDestImg, srcET, destET, R.drawable.switchfromhuji)
        }
        vm.toHuji = toHuji
    }



    private fun designSwitchDirection(img:ImageView, constWay: EditText,
                                      notConstWay: EditText, resOfImg: Int) {
        img.setImageResource(resOfImg)
        constWay.setText(getString(R.string.destHujiField))
        constWay.setTextColor(Color.BLACK)
        notConstWay.isEnabled = true
        constWay.isEnabled = false
        if (vm.srcOrDest != "") {
            notConstWay.setText(vm.srcOrDest)
        } else {

            notConstWay.text?.clear()
        }
    }






}