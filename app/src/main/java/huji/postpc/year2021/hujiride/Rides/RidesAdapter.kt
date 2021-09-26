package huji.postpc.year2021.hujiride.Rides

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.R
import huji.postpc.year2021.hujiride.database.Ride
import java.text.SimpleDateFormat


class RidesAdapter: RecyclerView.Adapter<RideViewHolder>() {

    private val _ridesList: MutableList<Ride> = ArrayList()

    var onItemClickCallback: ((Ride)->Unit)? = null


    @SuppressLint("NotifyDataSetChanged")
    fun setRidesList(newRidesList: List<Ride>){
        _ridesList.clear()
        _ridesList.addAll(newRidesList)
//        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ride_item, parent, false)

        val holder = RideViewHolder(view)
        view.setOnClickListener{
            val callback = onItemClickCallback?: return@setOnClickListener
            val ride = _ridesList[holder.adapterPosition]
            callback(ride)
        }
        return holder

    }

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        val ride = _ridesList[position]
        holder.source.text = "HUJI"
        holder.dest.text = "HUJI"

        val stamp = ride.timeStamp
        val dt = stamp.toDate()
        val datFrm = SimpleDateFormat("dd/MM")
        val timeFrm = SimpleDateFormat("HH:mm")
        val timeStr= "${timeFrm.format(dt)}   ${datFrm.format(dt)}"

        holder.time.text = timeStr

        if (ride.isDestinationHuji){
            holder.source.text = ride.destName
        }else{
            holder.dest.text = ride.destName
        }

//        holder.infoImg.setOnClickListener {
//            val callback = onItemClickCallback?: return@setOnClickListener
//            callback(ride)
//        }
    }

    override fun getItemCount(): Int {
        return _ridesList.size
    }


}