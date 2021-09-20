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
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val contex = parent.context
        val view = LayoutInflater.from(contex).inflate(R.layout.group_item, parent, false)
        app = HujiRideApplication.getInstance()

        val clientId = app.userDetails.clientUniqueID

        val holder = GroupViewHolder(view)
        view.setOnClickListener{
            val callback = onItemClickCallback?: return@setOnClickListener
//            val groupsList = app.groupsData.getGroups()
            val group = clientsGroupsList[holder.adapterPosition]
            callback(group)
//            GlobalScope.launch (Dispatchers.IO) {
//                val groupsList = app.db.getGroupsOfClient(clientId)
//                val group = groupsList[holder.adapterPosition]
//                callback(group)
//            }
        }

        view.findViewById<ImageView>(R.id.delete_img).setOnClickListener {
            val callback = onDeleteIconCallback?: return@setOnClickListener
//            val groupsList = app.groupsData.getGroups()
            GlobalScope.launch (Dispatchers.IO) {
                val groupsList = app.db.getGroupsOfClient(clientId)
                val group = groupsList?.get(holder.adapterPosition)
                group?.let { it1 -> callback(it1) }
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        app = HujiRideApplication.getInstance()

//        val clientId = app.userDetails.clientUniqueID
        val group = clientsGroupsList[position]
        holder.name.text = group
        holder.name.setOnClickListener {
            val callback = onItemClickCallback?: return@setOnClickListener
            callback(group)
        }


//        GlobalScope.launch (Dispatchers.IO) {
//            val groupsList = app.db.getGroupsOfClient(clientId)
//
//            withContext(Dispatchers.Main) {
//                val group = groupsList[position]
//                holder.name.text = group
//                holder.name.setOnClickListener {
//                    val callback = onItemClickCallback?: return@setOnClickListener
//                    callback(group)
//                }
//            }
//
//
//        }






    }

    override fun getItemCount(): Int {
//        app = HujiRideApplication.getInstance()

//        val groupsList = HujiRideApplication.getInstance().groupsData.getGroups()
//        val clientId = app.userDetails.clientUniqueID

//        val groupsList = app.db.getGroupsOfClient(clientId)


//        return groupsList.size
        return clientsGroupsList.size
    }
}