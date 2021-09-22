package huji.postpc.year2021.hujiride.MyRides
import java.util.*
import kotlin.collections.ArrayList


class MyRides {

    private var ridesList: ArrayList<String> = arrayListOf()
//    private var ridesList: ArrayList<Ride> = arrayListOf()
//    private var idSet: Set<UUID> = setOf()


    fun addRide(rideId: String){
        if (!ridesList.contains(rideId)){
            ridesList.add(rideId)
        }
    }


    fun getRidesList() : ArrayList<String>
    {
        val newRides: ArrayList<String> = arrayListOf()
        newRides.addAll(ridesList)
        return newRides
    }




}