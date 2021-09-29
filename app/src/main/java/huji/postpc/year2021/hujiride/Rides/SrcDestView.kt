package huji.postpc.year2021.hujiride.Rides

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.ContextMenu
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.findFragment
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import huji.postpc.year2021.hujiride.R

class SrcDestView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    init {
        inflate(context, R.layout.src_dest_view, this)
    }

    private lateinit var srcDestImg: ImageView
    private lateinit var switchDirBtn: ImageButton

    private lateinit var srcACSF: AutocompleteSupportFragment
    private lateinit var srcEditText: EditText
    private lateinit var srcSearchButton: View

    private lateinit var destACSF: AutocompleteSupportFragment
    private lateinit var destEditText: EditText
    private lateinit var destSearchButton: View

    public var onSrcDestPick: ((String, LatLng?) -> Unit)? = null

    var toHuji: Boolean = true
        private set

    override fun onFinishInflate() {
        super.onFinishInflate()
        srcDestImg = findViewById(R.id.srcDestImg)
        switchDirBtn = findViewById(R.id.switchDirectionBtn)



        srcACSF = (context as AppCompatActivity).supportFragmentManager.findFragmentById(R.id.place_autocomplete_fragment_src) as AutocompleteSupportFragment
        srcACSF.requireView().apply {
            srcEditText =
                findViewById(com.google.android.libraries.places.R.id.places_autocomplete_search_input)
            srcSearchButton =
                findViewById(com.google.android.libraries.places.R.id.places_autocomplete_search_button)
        }

        destACSF = (context as AppCompatActivity).supportFragmentManager.findFragmentById(R.id.place_autocomplete_fragment_dest) as AutocompleteSupportFragment
        destACSF.requireView().apply {
            destEditText =
                findViewById(com.google.android.libraries.places.R.id.places_autocomplete_search_input)
            destSearchButton =
                findViewById(com.google.android.libraries.places.R.id.places_autocomplete_search_button)
        }
        autoCompletePlaces(srcACSF)
        autoCompletePlaces(destACSF)

        switchDirBtn.setOnClickListener { switchDirection() }
        setDirection(toHuji)
    }


    private fun autoCompletePlaces(autocompleteFragment: AutocompleteSupportFragment) {
        // places search bar
        Places.initialize(context, "AIzaSyDTcekEAFGq-VG0MCPTNsYSwt9dKI8rIZA")
        val placesClient = Places.createClient(context)

        // Specify the types of place data to return.
        autocompleteFragment
            .setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
            .setCountry("IL")
            .setHint("Search a Place...")

        val TAG = "SEARCH"
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                onSrcDestPick?.invoke(place.name.toString(), place.latLng)
            }
            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.e(TAG, "An error occurred: $status")
            }
        })
    }

    fun setSrcEnabled(isEnabled: Boolean) {
        srcEditText.isEnabled = isEnabled
        srcSearchButton.isEnabled = isEnabled
    }

    fun setDestEnabled(isEnabled: Boolean) {
        destEditText.isEnabled = isEnabled
        destSearchButton.isEnabled = isEnabled
    }

    fun setDirection(toHuji: Boolean) {
        setSrcEnabled(toHuji)
        setDestEnabled(!toHuji)
        srcDestImg.setImageResource(if (toHuji) R.drawable.to_huji else R.drawable.to_home)

        val srcText = srcEditText.text
        val destText = destEditText.text

        if (toHuji) {
            destACSF.setText("HUJI")
            srcACSF.setText(destText)
        } else {
            destACSF.setText(srcText)
            srcACSF.setText("HUJI")
        }
    }

    fun switchDirection() {
        setDirection(!toHuji)
        toHuji = !toHuji

    }
}