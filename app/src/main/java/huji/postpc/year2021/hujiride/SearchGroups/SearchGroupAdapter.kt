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
        val group = app.groupsData.mutableDataCheckedGroups.value?.get(position)
        if (group != null) {
            holder.checkBox.text = group.name
            holder.checkBox.isChecked = group.checked

            holder.checkBox.setOnClickListener {
                app.groupsData.mutableDataCheckedGroups.value?.get(position)!!.checked = holder.checkBox.isChecked
            }

            holder.checkBox.setOnCheckedChangeListener{ _, isChecked ->
                app.groupsData.mutableDataCheckedGroups.value?.get(position)!!.checked = isChecked
                app.groupsData.currentChanges.add(group)

            }

        }

    }

    override fun getItemCount(): Int {
        val app = HujiRideApplication.getInstance()

        return app.groupsData.mutableDataCheckedGroups.value?.size!!
    }


}