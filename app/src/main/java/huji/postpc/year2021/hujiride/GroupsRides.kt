package huji.postpc.year2021.hujiride
import huji.postpc.year2021.hujiride.Rides.Ride

import java.util.*

class GroupsRides {

    var idSet: Set<UUID> = setOf()
    var ridesList: List<Ride> = arrayListOf()

    fun addRide(ride: Ride){
        val id = ride.id
        if (!idSet.contains(id)){
            idSet = idSet + id
            ridesList = ridesList + ride
        }

    }
}