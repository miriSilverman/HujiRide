package huji.postpc.year2021.hujiride.Rides

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem


class RidesViewModel: ViewModel() {

    var pressedRide: MutableLiveData<Ride> = MutableLiveData()

    var pressedGroup: MutableLiveData<SearchGroupItem> = MutableLiveData()




}