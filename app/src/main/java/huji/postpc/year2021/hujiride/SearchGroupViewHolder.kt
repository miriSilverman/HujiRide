package huji.postpc.year2021.hujiride

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchGroupViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val checkBox : CheckBox = view.findViewById(R.id.checkBox)
}