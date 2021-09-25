package huji.postpc.year2021.hujiride

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import huji.postpc.year2021.hujiride.Rides.RidesViewModel

class SetDestinationDialog : AppCompatDialogFragment() {
    private lateinit var autoCompleteFrag : AutocompleteSupportFragment
//    var onReportCallback: (()->Unit)? = null
    private var srcOrDestStr = ""
    private var latLng: LatLng = LatLng(0.0, 0.0)


//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val builder = AlertDialog.Builder(activity)
//
//        val inflater = activity?.layoutInflater
//
//        val aView = inflater?.inflate(R.layout.set_direction_dialog, null)
//
////        autoCompleteFrag = childFragmentManager.findFragmentById(R.id.place_autocomplete_fragment)
////                as AutocompleteSupportFragment
////
//        autoCompleteFrag = requireActivity().supportFragmentManager.findFragmentById(R.id.place_autocomplete_fragment)
//        as AutocompleteSupportFragment
//
//        autoCompletePlaces(autoCompleteFrag)
//
//
//        builder.setView(aView)
//            .setTitle("Select destination")
//            .setNegativeButton("cancel", DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int -> })
//            .setPositiveButton("select", DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int ->
////                onReportCallback?.invoke()
//                val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
//                vm.latLng = latLng
//                vm.srcOrDest = srcOrDestStr
//            })
//
//
//        return builder.create()
//
//
//    }


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


}