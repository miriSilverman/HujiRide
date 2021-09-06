package huji.postpc.year2021.hujiride

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class GroupsAdapter: RecyclerView.Adapter<GroupViewHolder>() {



    var onItemClickCallback: ((Group)->Unit)? = null
    var onDeleteIconCallback: ((Group)->Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val contex = parent.context
        val view = LayoutInflater.from(contex).inflate(R.layout.group_item, parent, false)

        val holder = GroupViewHolder(view)
        view.setOnClickListener{
            val callback = onItemClickCallback?: return@setOnClickListener
            val _groupsList = HujiRideApplication.getInstance().groupsData.getGroups()
            val group = _groupsList[holder.adapterPosition]
            callback(group)
        }

        view.findViewById<ImageView>(R.id.delete_img).setOnClickListener {
            val callback = onDeleteIconCallback?: return@setOnClickListener
            val _groupsList = HujiRideApplication.getInstance().groupsData.getGroups()
            val group = _groupsList[holder.adapterPosition]
            callback(group)
        }
        return holder
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val _groupsList = HujiRideApplication.getInstance().groupsData.getGroups()
        val group = _groupsList[position]
        holder.name.setText(group.name)

        holder.name.setOnClickListener {
            val callback = onItemClickCallback?: return@setOnClickListener
            callback(group)
        }
    }

    override fun getItemCount(): Int {
        val _groupsList = HujiRideApplication.getInstance().groupsData.getGroups()
        return _groupsList.size
    }
}