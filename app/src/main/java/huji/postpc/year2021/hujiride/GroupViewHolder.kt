package huji.postpc.year2021.hujiride

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroupViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val name : TextView = view.findViewById(R.id.groups_name)
}