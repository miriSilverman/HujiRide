package huji.postpc.year2021.hujiride.Groups

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.HujiRideApplication
import huji.postpc.year2021.hujiride.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupsAdapter: RecyclerView.Adapter<GroupViewHolder>() {


    private lateinit var app :HujiRideApplication
    private var clientsGroupsList = ArrayList<String>()

    var onItemClickCallback: ((String)->Unit)? = null
    var onDeleteIconCallback: ((String)->Unit)? = null


    @SuppressLint("NotifyDataSetChanged")
    fun setGroupsList(list: ArrayList<String>){
        clientsGroupsList = list
//        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val contex = parent.context
        val view = LayoutInflater.from(contex).inflate(R.layout.group_item, parent, false)
        app = HujiRideApplication.getInstance()

        val clientId = app.userDetails.clientUniqueID

        val holder = GroupViewHolder(view)
        view.setOnClickListener{
            val callback = onItemClickCallback?: return@setOnClickListener
            val group = clientsGroupsList[holder.adapterPosition]
            callback(group)
        }

        view.findViewById<ImageView>(R.id.delete_img).setOnClickListener {
            val callback = onDeleteIconCallback?: return@setOnClickListener
            GlobalScope.launch (Dispatchers.IO) {
                val groupsList = app.db.getGroupsOfClient(clientId)
                val group = groupsList?.get(holder.adapterPosition)
                group?.let { it1 -> callback(it1) }
            }
        }
        return holder
    }

    fun getGroupsName(id: String) : String?{
        return app.jerusalemNeighborhoods[id]
    }


    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        app = HujiRideApplication.getInstance()

        val group = clientsGroupsList.toArray()[position]
        holder.name.text = getGroupsName(group.toString())
        holder.name.setOnClickListener {
            val callback = onItemClickCallback?: return@setOnClickListener
            callback(group.toString())
        }


    }

    override fun getItemCount(): Int {
        return clientsGroupsList.size
    }
}