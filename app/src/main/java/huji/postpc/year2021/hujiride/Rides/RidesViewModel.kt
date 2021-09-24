package huji.postpc.year2021.hujiride.Rides

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem
import huji.postpc.year2021.hujiride.database.Ride


class RidesViewModel: ViewModel() {

    var pressedRide: MutableLiveData<Ride> = MutableLiveData()

    var pressedGroup: MutableLiveData<SearchGroupItem> = MutableLiveData()
    var latLng : LatLng = LatLng(0.0, 0.0)

    var toHuji: Boolean = true
    var srcOrDest: String = ""

    var fromMyRides :Boolean = true

}