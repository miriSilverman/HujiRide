package huji.postpc.year2021.hujiride.Rides

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
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
//    private var setDirecDialogView: View? = null
//    private var toHuji: Boolean = true
    private lateinit var img: ImageView
    private lateinit var noRidesTxt: TextView

    private lateinit var sortTIL: TextInputLayout
    private lateinit var sortACTV: AutoCompleteTextView

    private lateinit var filterTIL: TextInputLayout
    private lateinit var filterACTV: AutoCompleteTextView

    private lateinit var adapter: RidesAdapter
    private lateinit var vm: RidesViewModel
    private lateinit var app: HujiRideApplication
    private lateinit var progressBar: ProgressBar
    private lateinit var addRideBtn: Button
    private lateinit var applyBtn: ImageButton
    private lateinit var ridesRecycler: RecyclerView
    private var ridesList: ArrayList<Ride> = arrayListOf()
    private var newList = ArrayList<Ride>()
    private var dbRidesArr = ArrayList<Ride>()
    private var sortedAllocationDbRidesArr = ArrayList<Ride>()
    private lateinit var autoCompleteFrag: AutocompleteSupportFragment



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

        vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)

        if (vm.srcOrDest != "") {
            autoCompleteFrag.setText(vm.srcOrDest)
        }

        ridesRecycler.adapter = adapter
        ridesRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)


        adapter.onItemClickCallback = { ride: Ride ->
            vm.pressedRide.value = ride
            Navigation.findNavController(aView).navigate(R.id.action_ridesList_to_ridesDetails)
        }

        sortACTV.setOnItemClickListener { parent, _, pos, _ ->

            if (parent.getItemAtPosition(pos).toString() == "src and dest") {
                setDestinationSorting()
            }else{
                autoCompleteFrag.requireView().visibility = View.INVISIBLE
            }
        }

        applyBtn.setOnClickListener {
            applyBtnOnPressed()
        }


        setAdaptersList()

        return aView
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAdaptersList() {
        val curGroup = vm.pressedGroup
        val group = curGroup.value?.name
        var groupsName: String? = null
        if (group != null) {
            groupsName = group.toString()
        }

        GlobalScope.launch(Dispatchers.IO) {

            if (vm.latLng != null) {
                sortedAllocationDbRidesArr = app.db.sortRidesAccordingToALocation(vm.latLng!!)
            }

            dbRidesArr = app.db.getRidesListOfGroup(groupsName)

            ridesList.clear()
            ridesList.addAll(dbRidesArr)
            adapter.setRidesList(ridesList)

            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
                if (adapter.itemCount == 0) {
                    noNearRidesCase()
                } else {
                    thereAreRidesCase()
                }
            }

        }


    }


    @SuppressLint("NotifyDataSetChanged")
    private fun applyBtnOnPressed() {
        when (sortACTV.text.toString()) {
            "no sorting" -> noSorting()
            "time" -> sortAccordingToTime()
            "src and dest" -> sortAccordingToAlloc()
        }

        if (sortACTV.text.toString() != "src and dest"){
            filteringSwitchCase()
        }

    }

    private fun setDestinationSorting() {
        autoCompleteFrag.requireView().visibility = View.VISIBLE
        autoCompletePlaces(autoCompleteFrag)
//        settingPressed()
//        val dialog = SetDestinationDialog()
//
//        dialog.show(activity?.supportFragmentManager!!, "dest dialog")
    }



//    @SuppressLint("UseSwitchCompatOrMaterialCode")
//    private fun agreeToTermsDialog() {
//
//        var dialog: AlertDialog? = null
//        val builder = AlertDialog.Builder(activity)
//
//        val view = layoutInflater.inflate(R.layout.agree_to_terms, null)
//        val checkBox = view.findViewById<CheckBox>(R.id.terms_checkbox)
//
//        builder.setPositiveButton("Next", DialogInterface.OnClickListener { d, m ->
//        })
//
//
//        builder.setView(view)
//        dialog = builder.create()
//        dialog.setOnShowListener {
//            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
//
//            checkBox.setOnClickListener {
//                dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = checkBox.isChecked
//            }
//
//        }
//
//        dialog.show()
//
//    }


//
//    @SuppressLint("UseSwitchCompatOrMaterialCode")
//    private fun settingPressed() {
//
//        var dialog: AlertDialog? = null
//        val builder = AlertDialog.Builder(activity)
//
//        val view = layoutInflater.inflate(R.layout.setting_alert_dialog, null)
//
//        builder.setPositiveButton("Done", DialogInterface.OnClickListener { d, m ->
//            Toast.makeText(activity, "changed!", Toast.LENGTH_SHORT).show()
//        })
//
//
//        builder.setView(view)
//        dialog = builder.create()
//        dialog.show()
//
//    }


    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun insertDestDialog() {

        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(activity)

        val view = layoutInflater.inflate(R.layout.set_direction_dialog, null)

        builder.setPositiveButton("Got it") { _, _ ->
        }


        builder.setView(view)
        dialog = builder.create()
        dialog.show()

    }


    private fun autoCompletePlaces(autocompleteFragment: AutocompleteSupportFragment) {
        // places search bar
        Places.initialize(requireActivity(), "AIzaSyDTcekEAFGq-VG0MCPTNsYSwt9dKI8rIZA")
//        val placesClient = Places.createClient(requireActivity())

        // Specify the types of place data to return.
        autocompleteFragment
            .setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
            .setCountry("IL")
            .setHint("חפש מוצא...") // TODO translated version?

        val tag = "SEARCH"
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i(tag, "Place: ${place.name}, ${place.id}, ${place.latLng}")
                vm.srcOrDest = place.name.toString()
                if (place.latLng != null) {
                    vm.latLng = place.latLng!!
                }

            }



            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(tag, "An error occurred: $status")
            }
        })

    }


    private fun selectorTime(ride: Ride): java.util.Date {
        return ride.timeStamp.toDate()
    }

    private fun noSorting() {
        ridesList.clear()
        ridesList.addAll(dbRidesArr)
    }

    private fun sortAccordingToTime() {
        ridesList.clear()
        ridesList.addAll(dbRidesArr)
        ridesList.sortBy { selectorTime(it) }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortAccordingToAlloc() {
        if (vm.latLng == null) {
            insertDestDialog()
//            agreeToTermsDialog()

        } else {

            GlobalScope.launch(Dispatchers.IO) {
                if (vm.latLng != null) {
                    sortedAllocationDbRidesArr.clear()
                    sortedAllocationDbRidesArr.addAll(app.db.sortRidesAccordingToALocation(vm.latLng!!))
                }

                withContext(Dispatchers.Main) {
                    ridesList.clear()
                    ridesList.addAll(sortedAllocationDbRidesArr)

                    filteringSwitchCase()

                }

            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filteringSwitchCase() {
        when (filterACTV.text.toString()) {
            "source to Huji" -> filterToHuji(true)
            "Huji to destination" -> filterToHuji(false)
            "all" -> filterAll()
        }



        adapter.setRidesList(newList)
        adapter.notifyDataSetChanged()
    }


    private fun filterToHuji(condition: Boolean) {
            newList.clear()
            for (r in ridesList) {
                if (r.isDestinationHuji == condition) {
                    newList.add(r)
                }
            }
        }

        private fun filterAll() {
            newList.clear()
            newList.addAll(ridesList)
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
            applyBtn = aView.findViewById(R.id.apply_btn)
            autoCompleteFrag =
                childFragmentManager.findFragmentById(R.id.place_autocomplete_fragment)
                        as AutocompleteSupportFragment

        }

        private fun setVisibility(
            oneDirection: Int,
            secondDirection: Int,
            btnState: Boolean,
            progressbarVis: Int
        ) {
            addRideBtn.isEnabled = btnState

            progressBar.visibility = progressbarVis

            noRidesTxt.visibility = oneDirection
            img.visibility = oneDirection

            ridesRecycler.visibility = secondDirection
            sortTIL.visibility = secondDirection
            filterTIL.visibility = secondDirection
            applyBtn.visibility = secondDirection
            autoCompleteFrag.requireView().visibility = View.INVISIBLE
//        switchDirectionBtn.visibility = secondDirection
//        srcDestImg.visibility = secondDirection

        }


        private fun noNearRidesCase() {
            setVisibility(View.VISIBLE, View.INVISIBLE, true, View.INVISIBLE)
        }

        private fun thereAreRidesCase() {
            setVisibility(View.INVISIBLE, View.VISIBLE, true, View.INVISIBLE)
        }


//
//    @SuppressLint("UseSwitchCompatOrMaterialCode", "InflateParams")
//    private fun settingPressedddd()
//    {
//
//        var dialog: AlertDialog? = null
//        val builder = AlertDialog.Builder(activity)
//
//        if (setDirecDialogView == null){
//            setDirecDialogView = layoutInflater.inflate(R.layout.set_direction_dialog, null)
//        }
////        val autoCompleteFrag = childFragmentManager.findFragmentById(R.id.place_autocomplete_fragment)
////                as AutocompleteSupportFragment
////
////        autoCompletePlaces(autoCompleteFrag)
//
//        builder.setView(setDirecDialogView)
//            .setTitle("Select destination")
//            .setNegativeButton("cancel", DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int -> })
//            .setPositiveButton("select", DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int ->
////                onReportCallback?.invoke()
////                val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
////                vm.latLng = latLng
////                vm.srcOrDest = srcOrDestStr
//            })
//
//        dialog = builder.create()
//        dialog.show()
//
//    }



    }






