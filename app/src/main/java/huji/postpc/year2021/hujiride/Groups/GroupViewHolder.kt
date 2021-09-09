package huji.postpc.year2021.hujiride.Groups

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.R

class GroupViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val name : TextView = view.findViewById(R.id.groups_name)
}