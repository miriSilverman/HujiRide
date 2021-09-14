package huji.postpc.year2021.hujiride
import huji.postpc.year2021.hujiride.Rides.Ride

class RidesPerGroups{


    var ridesGilo = GroupsRides()
    var ridesBakaa = GroupsRides()
    var ridesMalcha = GroupsRides()
    var ridesTalpiyot = GroupsRides()
    var ridesPisgat = GroupsRides()
    var ridesAll = GroupsRides()



        var map: Map<String, GroupsRides> = mapOf(
            "Gilo" to ridesGilo,
            "Bakaa" to ridesBakaa,
            "Malcha" to ridesMalcha,
            "Talpiyot" to ridesTalpiyot,
            "Pisgat zeev" to ridesPisgat,
            "all" to ridesAll
    )


    fun addRide(ride: Ride, group: String){
        val groupList = map[group]
        if (groupList != null) {
            groupList.addRide(ride)

        }
        ridesAll.addRide(ride)
    }









}


