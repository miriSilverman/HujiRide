package huji.postpc.year2021.hujiride

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.Rides.Ride
import huji.postpc.year2021.hujiride.Rides.RideViewHolder

class MyRidesAdapter: RecyclerView.Adapter<MyRidesViewHolder>() {


    var onItemClickCallback: ((Ride)->Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRidesViewHolder {
        val contex = parent.context
        val view = LayoutInflater.from(contex).inflate(R.layout.ride_item, parent, false)

        var holder = MyRidesViewHolder(view)

        val ridesList = HujiRideApplication.getInstance().myRides.getRidesList()

        view.setOnClickListener{
            val callback = onItemClickCallback?: return@setOnClickListener
            val ride = ridesList[holder.adapterPosition]
            callback(ride)
        }
        return holder
    }

    override fun onBindViewHolder(holder: MyRidesViewHolder, position: Int) {
        val ridesList = HujiRideApplication.getInstance().myRides.getRidesList()

        val ride = ridesList[position]
        holder.source.setText(ride.src)
        holder.dest.setText(ride.dest)
        holder.time.setText(ride.time)

        holder.infoImg.setOnClickListener {
            val callback = onItemClickCallback?: return@setOnClickListener
            callback(ride)
        }


    }

    override fun getItemCount(): Int {
        val ridesList = HujiRideApplication.getInstance().myRides.getRidesList()
        return ridesList.size

    }
}