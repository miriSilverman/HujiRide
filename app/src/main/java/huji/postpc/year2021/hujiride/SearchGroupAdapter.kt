package huji.postpc.year2021.hujiride

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SearchGroupAdapter : RecyclerView.Adapter<SearchGroupViewHolder>() {

    private val _groupsList: MutableList<SearchGroupItem> = ArrayList()

    public var onItemClickCallback: ((SearchGroupItem)->Unit)? = null


    fun setGroupsList(newGroupsList: List<SearchGroupItem>){
        _groupsList.clear()
        _groupsList.addAll(newGroupsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchGroupViewHolder {
        val contex = parent.context
        val view = LayoutInflater.from(contex).inflate(R.layout.search_group_item, parent, false)

        var holder = SearchGroupViewHolder(view)
        view.setOnClickListener{
            val callback = onItemClickCallback?: return@setOnClickListener
            val group = _groupsList[holder.adapterPosition]
            callback(group)
        }
        return holder

    }

    override fun onBindViewHolder(holder: SearchGroupViewHolder, position: Int) {
        val group = _groupsList[position]
        holder.checkBox.setText(group.name)

        holder.checkBox.setOnClickListener {
            val callback = onItemClickCallback?: return@setOnClickListener
            callback(group)
        }
    }

    override fun getItemCount(): Int {
        return _groupsList.size
    }


}