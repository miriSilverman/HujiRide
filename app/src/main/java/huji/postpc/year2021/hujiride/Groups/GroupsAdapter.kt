package huji.postpc.year2021.hujiride.Groups

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.HujiRideApplication
import huji.postpc.year2021.hujiride.R
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem

class GroupsAdapter: RecyclerView.Adapter<GroupViewHolder>() {



    var onItemClickCallback: ((SearchGroupItem)->Unit)? = null
    var onDeleteIconCallback: ((SearchGroupItem)->Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val contex = parent.context
        val view = LayoutInflater.from(contex).inflate(R.layout.group_item, parent, false)

        val holder = GroupViewHolder(view)
        view.setOnClickListener{
            val callback = onItemClickCallback?: return@setOnClickListener
            val groupsList = HujiRideApplication.getInstance().groupsData.getGroups()
            val group = groupsList[holder.adapterPosition]
            callback(group)
        }

        view.findViewById<ImageView>(R.id.delete_img).setOnClickListener {
            val callback = onDeleteIconCallback?: return@setOnClickListener
            val groupsList = HujiRideApplication.getInstance().groupsData.getGroups()
            val group = groupsList[holder.adapterPosition]
            callback(group)
        }
        return holder
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val groupsList = HujiRideApplication.getInstance().groupsData.getGroups()
        val group = groupsList[position]
        holder.name.text = group.name

        holder.name.setOnClickListener {
            val callback = onItemClickCallback?: return@setOnClickListener
            callback(group)
        }
    }

    override fun getItemCount(): Int {
        val groupsList = HujiRideApplication.getInstance().groupsData.getGroups()
        return groupsList.size
    }
}