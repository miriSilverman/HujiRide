package huji.postpc.year2021.hujiride.Groups

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.R

class GroupViewHolder(view: View, editTime: Boolean): RecyclerView.ViewHolder(view) {
    val name : TextView = view.findViewById(R.id.groups_name)
    private var deleteBtn : ImageView = view.findViewById(R.id.delete_img)
    private var nextImageView: ImageView = view.findViewById(R.id.next)

    init {
        if (editTime){
            nextImageView.visibility = View.INVISIBLE
            deleteBtn.visibility = View.VISIBLE

        }else{
            nextImageView.visibility = View.VISIBLE
            deleteBtn.visibility = View.INVISIBLE
        }

    }

}