package huji.postpc.year2021.hujiride

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.Groups.Group

class SearchGroupAdapter : RecyclerView.Adapter<SearchGroupViewHolder>() {

//    private val _groupsList: MutableList<SearchGroupItem> = ArrayList()

    var onItemClickCallback: ((SearchGroupItem)->Unit)? = null
    var searchCallback : SearchCallback ?= null


//    fun setGroupsList(newGroupsList: List<SearchGroupItem>){
//        _groupsList.clear()
//        _groupsList.addAll(newGroupsList)
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchGroupViewHolder {
        val contex = parent.context
        val view = LayoutInflater.from(contex).inflate(R.layout.search_group_item, parent, false)

        var holder = SearchGroupViewHolder(view)
//        view.setOnClickListener{
//            val callback = onItemClickCallback?: return@setOnClickListener
//            val group = app.groupsData.liveDataCheckedGroups[holder.adapterPosition]
//            callback(group)
//        }
        return holder

    }

    override fun onBindViewHolder(holder: SearchGroupViewHolder, position: Int) {
        val app = HujiRideApplication.getInstance()
        val group = app.groupsData.mutableDataCheckedGroups.value?.get(position)
        if (group != null) {
            holder.checkBox.setText(group.name)
            holder.checkBox.isChecked = group.checked


            holder.checkBox.setOnClickListener { view ->
                app.groupsData.mutableDataCheckedGroups.value?.get(position)!!.checked = holder.checkBox.isChecked

//            searchCallback?.onCheckingItem(app.groupsData.mutableDataCheckedGroups.value as java.util.ArrayList<SearchGroupItem>?)
            }

            holder.checkBox.setOnCheckedChangeListener{buttonView, isChecked ->
                app.groupsData.mutableDataCheckedGroups.value?.get(position)!!.checked = isChecked
            }



        }




//        holder.checkBox.setOnClickListener {
//            val callback = onItemClickCallback?: return@setOnClickListener
//            callback(group)
//        }



    }

    override fun getItemCount(): Int {
        val app = HujiRideApplication.getInstance()

        return app.groupsData.mutableDataCheckedGroups.value?.size!!
    }


}