package huji.postpc.year2021.hujiride

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class GroupsAdapter: RecyclerView.Adapter<GroupViewHolder>() {

    private val _groupsList: MutableList<Group> = ArrayList()

    public var onItemClickCallback: ((Group)->Unit)? = null
    public var onDeleteIconCallback: ((Group)->Unit)? = null

    fun setGroupsList(newGroupsList: List<Group>){
        _groupsList.clear()
        _groupsList.addAll(newGroupsList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val contex = parent.context
        val view = LayoutInflater.from(contex).inflate(R.layout.group_item, parent, false)

        var holder = GroupViewHolder(view)
        view.setOnClickListener{
            val callback = onItemClickCallback?: return@setOnClickListener
            val group = _groupsList[holder.adapterPosition]
            callback(group)
        }

        view.findViewById<ImageView>(R.id.delete_img).setOnClickListener {
            val callback = onDeleteIconCallback?: return@setOnClickListener
            val group = _groupsList[holder.adapterPosition]
            callback(group)
        }
        return holder
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = _groupsList[position]
        holder.name.setText(group.name)

        holder.name.setOnClickListener {
            val callback = onItemClickCallback?: return@setOnClickListener
            callback(group)
        }
    }

    override fun getItemCount(): Int {
        return _groupsList.size
    }
}