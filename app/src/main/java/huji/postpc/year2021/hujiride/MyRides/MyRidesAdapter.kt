package huji.postpc.year2021.hujiride.MyRides

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.HujiRideApplication
import huji.postpc.year2021.hujiride.R
import huji.postpc.year2021.hujiride.Rides.Ride

class MyRidesAdapter: RecyclerView.Adapter<MyRidesViewHolder>() {


    var onItemClickCallback: ((Ride)->Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRidesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ride_item, parent, false)

        val holder = MyRidesViewHolder(view)

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
        holder.source.text = ride.src
        holder.dest.text = ride.dest
        holder.time.text = ride.time

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