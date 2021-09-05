package huji.postpc.year2021.hujiride

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RideViewHolder (view: View): RecyclerView.ViewHolder(view) {
    val source : TextView = view.findViewById(R.id.groups_name)
}