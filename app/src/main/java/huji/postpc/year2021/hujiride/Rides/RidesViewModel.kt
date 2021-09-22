package huji.postpc.year2021.hujiride.Rides

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem
import huji.postpc.year2021.hujiride.database.Ride


class RidesViewModel: ViewModel() {

    var pressedRide: MutableLiveData<Ride> = MutableLiveData()

    var pressedGroup: MutableLiveData<SearchGroupItem> = MutableLiveData()


    var toHuji: Boolean = true
    var srcOrDest: String = ""

}