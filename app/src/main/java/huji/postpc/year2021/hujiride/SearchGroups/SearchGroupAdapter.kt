package huji.postpc.year2021.hujiride.SearchGroups

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.*

class SearchGroupAdapter : RecyclerView.Adapter<SearchGroupViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchGroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_group_item, parent, false)

        return SearchGroupViewHolder(view)

    }

    override fun onBindViewHolder(holder: SearchGroupViewHolder, position: Int) {
        val app = HujiRideApplication.getInstance()
        val group = app.groupsData.mutableDataFilteredGroups.value?.get(position)
        if (group != null) {
//            holder.checkBox.text = group.name
            holder.checkBox.text = group
            val indexOfGroup = app.groupsData.neighborhoods.indexOf(group)
            holder.checkBox.isChecked = app.groupsData.neighborhoodsChecked[indexOfGroup]
//            holder.checkBox.isChecked = group.checked
//            holder.checkBox.isChecked = checkIfChecked(group.name)

            holder.checkBox.setOnClickListener {
//                app.groupsData.mutableDataFilteredGroups.value?.get(position)!!.checked = holder.checkBox.isChecked
                if (holder.checkBox.isChecked){
                    app.groupsData.addGroup(SearchGroupItem(group, true))
                }else{
                    app.groupsData.removeGroup(SearchGroupItem(group, true))
                }
            }

            holder.checkBox.setOnCheckedChangeListener{ _, isChecked ->
//                app.groupsData.mutableDataFilteredGroups.value?.get(position)!!.checked = isChecked
//                app.groupsData.currentChanges.add(group)
              if (isChecked){
                  app.groupsData.addGroup(SearchGroupItem(group, true))
              }else{
                  app.groupsData.removeGroup(SearchGroupItem(group, true))
              }
            }

        }

    }
//
//    fun checkIfChecked(name: String): Boolean{
//        val app = HujiRideApplication.getInstance()
//
////        val idx = app.groupsData.neighborhoods.indexOf(SearchGroupItem(name, false))
////        return app.groupsData.neighborhoodsChecked[idx]
//
//
////        for (s :SearchGroupItem in app.groupsData.currentChanges) {
////            if (s.name.equals(name)) {
////                return true
////            }
////        }
////
////        for (s :SearchGroupItem in app.groupsData.getGroups()){
////            if (s.name.equals(name)){
////                return true
////            }
////        }
////        return false
//    }
//


    override fun getItemCount(): Int {
        val app = HujiRideApplication.getInstance()

        return app.groupsData.mutableDataFilteredGroups.value?.size!!
    }


}