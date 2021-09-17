package huji.postpc.year2021.hujiride.SearchGroups

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.*

class SearchGroupAdapter : RecyclerView.Adapter<SearchGroupViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchGroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_group_item, parent, false)

        return SearchGroupViewHolder(view)

    }

    override fun onBindViewHolder(holder: SearchGroupViewHolder, position: Int) {
        val app = HujiRideApplication.getInstance()
        val groupName = app.groupsData.mutableDataFilteredGroups.value?.get(position)
        if (groupName != null) {
            holder.checkBox.text = groupName
            holder.checkBox.isChecked = app.groupsData.getGroups().contains(groupName)

            holder.checkBox.setOnClickListener{
                if (holder.checkBox.isChecked){
                    app.groupsData.addGroup(groupName)
                    Toast.makeText(app, "$groupName was added to your groups", Toast.LENGTH_SHORT).show()
                }else{
                    app.groupsData.removeGroup(groupName)
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
        val app = HujiRideApplication.getInstance()

        return app.groupsData.mutableDataFilteredGroups.value?.size!!
    }


}