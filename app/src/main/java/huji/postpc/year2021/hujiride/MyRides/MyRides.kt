package huji.postpc.year2021.hujiride.MyRides
import huji.postpc.year2021.hujiride.Rides.Ride



class MyRides {

    private var ridesList: ArrayList<Ride> = arrayListOf()

    fun addRide(ride: Ride){
        ridesList.add(ride)
    }


    fun getRidesList() : ArrayList<Ride>
    {
        var newRides: ArrayList<Ride> = arrayListOf()
        newRides.addAll(ridesList)
        return newRides
    }




}