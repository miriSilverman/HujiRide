package huji.postpc.year2021.hujiride

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyRidesViewHolder (view: View): RecyclerView.ViewHolder(view) {
    val source : TextView = view.findViewById(R.id.src)
    val dest : TextView = view.findViewById(R.id.dest)
    val time : TextView = view.findViewById(R.id.time)
    val infoImg : ImageView = view.findViewById(R.id.info)

}