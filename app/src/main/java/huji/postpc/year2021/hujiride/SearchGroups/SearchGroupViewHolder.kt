package huji.postpc.year2021.hujiride.SearchGroups

import android.view.View
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.R

class SearchGroupViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val checkBox : CheckBox = view.findViewById(R.id.checkBox)
}