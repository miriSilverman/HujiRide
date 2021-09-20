package huji.postpc.year2021.hujiride.SearchGroups

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchGroupAdapter : RecyclerView.Adapter<SearchGroupViewHolder>() {


    private lateinit var app: HujiRideApplication
    private var clientsGroupsList = ArrayList<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun setGroupsList(list: ArrayList<String>) {
        clientsGroupsList = list
//        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchGroupViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_group_item, parent, false)

        return SearchGroupViewHolder(view)

    }


    override fun onBindViewHolder(holder: SearchGroupViewHolder, position: Int) {
        app = HujiRideApplication.getInstance()
        val groupName = app.groupsData.mutableDataFilteredGroups[position].second
        holder.checkBox.text = groupName
        val clientId = app.userDetails.clientUniqueID
        val a = getIdOfGroup(groupName)
        holder.checkBox.isChecked = clientsGroupsList.toArray().contains(a)



        holder.checkBox.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                if (holder.checkBox.isChecked) {
                    app.db.registerClientToGroup(clientId, getIdOfGroup(groupName).toInt())
                    app.db.getGroupsOfClient(clientId)?.let { it1 -> setGroupsList(it1) }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            app,
                            "$groupName was added to your groups",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                } else {
                    app.db.unregisterClientToGroup(clientId, getIdOfGroup(groupName))
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            app,
                            "$groupName was removed from your groups",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


            }


        }


    }


    override fun getItemCount(): Int {
        app = HujiRideApplication.getInstance()
        return app.groupsData.mutableDataFilteredGroups.size
    }


    private fun getIdOfGroup(groupName: String): String {
        val allGroups = app.jerusalemNeighborhoods
        for (pair in allGroups) {
            if (pair.value.equals(groupName)) {
                return pair.key
            }
        }
        return ""
    }


}