package huji.postpc.year2021.hujiride.SearchGroups

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.*

class SearchGroupAdapter : RecyclerView.Adapter<SearchGroupViewHolder>() {


    private lateinit var app : HujiRideApplication


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchGroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_group_item, parent, false)

        return SearchGroupViewHolder(view)

    }



    override fun onBindViewHolder(holder: SearchGroupViewHolder, position: Int) {
        app = HujiRideApplication.getInstance()
//        val groupName = app.groupsData.mutableDataFilteredGroups.value?.get(position)
        val groupName = app.groupsData.mutableDataFilteredGroups.get(position).second
        if (groupName != null) {
            holder.checkBox.text = groupName
            val clientId = app.userDetails.clientUniqueID
//            holder.checkBox.isChecked = app.groupsData.getGroups().contains(groupName)
            holder.checkBox.isChecked = app.db.getGroupsOfClient(clientId).contains(groupName)
            holder.checkBox.setOnClickListener{
                if (holder.checkBox.isChecked){
                    app.groupsData.addGroup(groupName)
//                    app.db.registerClientToGroup(clientId, getIdOfGroup(groupName))
                    Toast.makeText(app, "$groupName was added to your groups", Toast.LENGTH_SHORT).show()
                }else{
//                    app.groupsData.removeGroup(groupName)
                    app.db.unregisterClientToGroup(clientId, getIdOfGroup(groupName))
                    Toast.makeText(app, "$groupName was removed from your groups", Toast.LENGTH_SHORT).show()
                }
            }

//            holder.checkBox.setOnCheckedChangeListener{ _, isChecked ->
//              if (isChecked){
//                  app.groupsData.addGroup(groupName)
//              }else{
//                  app.groupsData.removeGroup(groupName)
//
//              }
//            }

        }

    }


    override fun getItemCount(): Int {
        app = HujiRideApplication.getInstance()
        return app.groupsData.mutableDataFilteredGroups.size
    }


    private fun getIdOfGroup(groupName: String) :String{
        val allGroups = app.jerusalemNeighborhoods
        for (pair in allGroups){
            if (pair.value.equals(groupName)){
                return pair.key
            }
        }
        return ""
    }


}