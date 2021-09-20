package huji.postpc.year2021.hujiride.MyRides
import huji.postpc.year2021.hujiride.Rides.Ride
import java.util.*
import kotlin.collections.ArrayList


class MyRides {

    private var ridesList: ArrayList<Ride> = arrayListOf()
    private var idSet: Set<UUID> = setOf()


//    fun addRide(ride: Ride){
//        val id = ride.id
//        if (!idSet.contains(id)){
//            idSet = idSet + id
//            ridesList.add(ride)
//        }
//    }


    fun getRidesList() : ArrayList<Ride>
    {
        val newRides: ArrayList<Ride> = arrayListOf()
        newRides.addAll(ridesList)
        return newRides
    }




}