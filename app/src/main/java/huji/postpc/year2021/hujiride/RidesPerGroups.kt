package huji.postpc.year2021.hujiride
import huji.postpc.year2021.hujiride.Rides.Ride
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem

class RidesPerGroups {


    val ridesAll: List<Ride> = arrayListOf(
            Ride("Gilo", "huji", "15:03", arrayListOf("gas station", "babies"), arrayListOf("no smoking", "just women"), "miri", "silverman", "0543697578"),
//            Ride("huji", "Gilo", "14:03", arrayListOf("gas station"), arrayListOf("put masks","no smoking"),"yair", "Gueta", "0547584545"),
//            Ride("Bakaa", "huji", "05:23", arrayListOf("gas station"), arrayListOf("no smoking", "vaccinated" ), "Shir", "Levi", "0536542897"),
//            Ride("huji", "Bakaa", "04:03", arrayListOf("gas station"), arrayListOf("no smoking"), "Dafna", "Golan", "0547587899"),
//            Ride("huji", "Bakaa", "15:03", arrayListOf("gas station"), arrayListOf("no smoking"), "Kobi", "Israeli", "0563698585"),
//            Ride("Talpiyot", "huji", "15:03", arrayListOf("gas station", "babies"), arrayListOf("no smoking", "just women"), "miri", "silverman", "0543697578"),
//            Ride("huji", "Talpiyot", "14:03", arrayListOf("gas station"), arrayListOf("put masks","no smoking"),"yair", "Gueta", "0547584545"),
//            Ride("Malcha", "huji", "05:23", arrayListOf("gas station"), arrayListOf("no smoking", "vaccinated" ), "Shir", "Levi", "0536542897"),
//            Ride("huji", "Malcha", "04:03", arrayListOf("gas station"), arrayListOf("no smoking"), "Dafna", "Golan", "0547587899"),
//            Ride("huji", "Malcha", "15:03", arrayListOf("gas station"), arrayListOf("no smoking"), "Kobi", "Israeli", "0563698585"),
//            Ride("Pisgat zeev", "huji", "05:23", arrayListOf("gas station"), arrayListOf("no smoking", "vaccinated" ), "Shir", "Levi", "0536542897"),
            Ride("huji", "Pisgat zeev", "04:03", arrayListOf("gas station"), arrayListOf("no smoking"), "Dafna", "Golan", "0547587899"),
            Ride("Pisgat zeev", "huji", "15:03", arrayListOf("gas station"), arrayListOf("no smoking"), "Kobi", "Israeli", "0563698585")
    )


    val ridesGilo: List<Ride> = arrayListOf(
            Ride("Gilo", "huji", "15:03", arrayListOf("gas station", "babies"), arrayListOf("no smoking", "just women"), "miri", "silverman", "0543697578"),
            Ride("huji", "Gilo", "14:03", arrayListOf("gas station"), arrayListOf("put masks","no smoking"),"yair", "Gueta", "0547584545"),
    )


    val ridesBakaa: List<Ride> = arrayListOf(
            Ride("Bakaa", "huji", "05:23", arrayListOf("gas station"), arrayListOf("no smoking", "vaccinated" ), "Shir", "Levi", "0536542897"),
            Ride("huji", "Bakaa", "04:03", arrayListOf("gas station"), arrayListOf("no smoking"), "Dafna", "Golan", "0547587899"),
            Ride("huji", "Bakaa", "15:03", arrayListOf("gas station"), arrayListOf("no smoking"), "Kobi", "Israeli", "0563698585")
    )


    val ridesTalpiyot: List<Ride> = arrayListOf(
            Ride("Talpiyot", "huji", "15:03", arrayListOf("gas station", "babies"), arrayListOf("no smoking", "just women"), "miri", "silverman", "0543697578"),
            Ride("huji", "Talpiyot", "14:03", arrayListOf("gas station"), arrayListOf("put masks","no smoking"),"yair", "Gueta", "0547584545"),
    )


    val ridesMalcha: List<Ride> = arrayListOf(
            Ride("Malcha", "huji", "05:23", arrayListOf("gas station"), arrayListOf("no smoking", "vaccinated" ), "Shir", "Levi", "0536542897"),
            Ride("huji", "Malcha", "04:03", arrayListOf("gas station"), arrayListOf("no smoking"), "Dafna", "Golan", "0547587899"),
            Ride("huji", "Malcha", "15:03", arrayListOf("gas station"), arrayListOf("no smoking"), "Kobi", "Israeli", "0563698585")
    )

    val ridesPisgat: List<Ride> = arrayListOf(
            Ride("Pisgat zeev", "huji", "05:23", arrayListOf("gas station"), arrayListOf("no smoking", "vaccinated" ), "Shir", "Levi", "0536542897"),
            Ride("huji", "Pisgat zeev", "04:03", arrayListOf("gas station"), arrayListOf("no smoking"), "Dafna", "Golan", "0547587899"),
            Ride("Pisgat zeev", "huji", "15:03", arrayListOf("gas station"), arrayListOf("no smoking"), "Kobi", "Israeli", "0563698585")
    )

    var map: Map<String, List<Ride>> = mapOf(
            "Gilo" to ridesGilo,
            "Bakaa" to ridesBakaa,
            "Malcha" to ridesMalcha,
            "Talpiyot" to ridesTalpiyot,
            "Pisgat zeev" to ridesPisgat,
            "all" to ridesAll
    )


    fun addRide(ride: Ride, group: String){
        val groupList = map[group] as ArrayList<Ride>
        groupList.add(ride)
    }









}


