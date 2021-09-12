package huji.postpc.year2021.hujiride.database

import android.content.Context
import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient

class PlacesSearchEngine (val appContext : Context) {
    var placesClient : PlacesClient

    init {
        Places.initialize(appContext, "AIzaSyDTcekEAFGq-VG0MCPTNsYSwt9dKI8rIZA")
        placesClient = Places.createClient(appContext)

    }

    fun searchQuery(query: String) {
        val token = AutocompleteSessionToken.newInstance()

        val findACReq = FindAutocompletePredictionsRequest.builder().apply {
            this.sessionToken = token
            this.query = query
            this.countries = listOf("israel")
            this.typeFilter = TypeFilter.ADDRESS
            this.locationBias = RectangularBounds.newInstance(
                LatLng(31.512156350042332, 35.38502722958169),
                LatLng(31.90577086360458, 35.06366299987831)
            )
        }.build()

        val TAG = "PLACES API"
        Log.i(TAG, "SENT: $query")

        placesClient.findAutocompletePredictions(findACReq).addOnSuccessListener { response ->
            for (prediction in response.autocompletePredictions) {
                Log.i(TAG, prediction.placeId)
                Log.i(TAG, prediction.getPrimaryText(null).toString())
            }
        }.addOnFailureListener { exception: Exception? ->
            if (exception is ApiException) {
                Log.e(TAG, "Place not found: " + exception.statusCode)
            }
        }
    }

}