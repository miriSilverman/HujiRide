package huji.postpc.year2021.hujiride

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RidesAdapter: RecyclerView.Adapter<RideViewHolder>() {

    private val _ridesList: MutableList<Ride> = ArrayList()

    public var onItemClickCallback: ((Ride)->Unit)? = null


    fun setRidesList(newRidesList: List<Ride>){
        _ridesList.clear()
        _ridesList.addAll(newRidesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        val contex = parent.context
        val view = LayoutInflater.from(contex).inflate(R.layout.ride_item, parent, false)

        var holder = RideViewHolder(view)
        view.setOnClickListener{
            val callback = onItemClickCallback?: return@setOnClickListener
            val ride = _ridesList[holder.adapterPosition]
            callback(ride)
        }
        return holder

    }

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        val ride = _ridesList[position]
        holder.source.setText(ride.src)
        holder.dest.setText(ride.dest)
        holder.time.setText(ride.time)

        holder.infoImg.setOnClickListener {
            val callback = onItemClickCallback?: return@setOnClickListener
            callback(ride)
        }
    }

    override fun getItemCount(): Int {
        return _ridesList.size
    }


}