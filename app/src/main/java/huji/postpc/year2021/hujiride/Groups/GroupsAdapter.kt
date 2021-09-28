package huji.postpc.year2021.hujiride.Groups

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.HujiRideApplication
import huji.postpc.year2021.hujiride.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GroupsAdapter: RecyclerView.Adapter<GroupViewHolder>() {


    private lateinit var app :HujiRideApplication
    private var clientsGroupsList = ArrayList<String>()

    var onItemClickCallback: ((String)->Unit)? = null
    var onDeleteIconCallback: ((String)->Unit)? = null
    private lateinit var activityForContext: Context
    private var editTime : Boolean = false
    private lateinit var deleteBtn : ImageView
    private lateinit var nextImageView: ImageView




    @SuppressLint("NotifyDataSetChanged")
    fun setGroupsList(list: ArrayList<String>){
        clientsGroupsList = list
    }

    fun setContext(context: Context){
        activityForContext = context
    }

    fun setEdit(boolean: Boolean){
        editTime = boolean
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.group_item, parent, false)
        app = HujiRideApplication.getInstance()

        val clientId = app.userDetails.clientUniqueID

        val holder = GroupViewHolder(view, editTime)
        view.setOnClickListener{
            val callback = onItemClickCallback?: return@setOnClickListener
            val group = clientsGroupsList[holder.adapterPosition]
            callback(group)
        }

        nextImageView = view.findViewById(R.id.next)
        deleteBtn = view.findViewById(R.id.delete_img)

        deleteBtn.setOnClickListener {
            val callback = onDeleteIconCallback?: return@setOnClickListener
            deleteGroup(clientId, holder, callback)
        }
        return holder
    }

    private fun getGroupsName(id: String) : String?{
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


    private fun deleteGroup(clientId: String, holder: GroupViewHolder, callback: ((String)->Unit)?){


        AlertDialog.Builder(activityForContext) // todo: maybe change to activity context
            .setTitle(R.string.unregisterGroupDialogTitle)
            .setMessage(R.string.unregisterGroupDialogTxt)
            .setIcon(R.drawable.ic_delete)
            .setCancelable(false)
            .setNegativeButton("no", null)
            .setPositiveButton("yes") { _: DialogInterface, _: Int ->

                GlobalScope.launch (Dispatchers.IO) {
                    val groupsList = app.db.getGroupsOfClient(clientId)
                    val group = groupsList[holder.adapterPosition]
                    group.let { it1 ->
                        if (callback != null) {
                            callback(it1)
                        }
                    }
                }

            }
            .create().show()
    }


}