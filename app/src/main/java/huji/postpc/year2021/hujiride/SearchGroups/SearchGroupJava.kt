package huji.postpc.year2021.hujiride.SearchGroups

import android.annotation.SuppressLint
import huji.postpc.year2021.hujiride.HujiRideApplication
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import huji.postpc.year2021.hujiride.R
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import android.text.TextWatcher
import android.text.Editable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class SearchGroupJava : Fragment() {
    private lateinit var adapter: SearchGroupAdapter
    private lateinit var neighborhoods: HashMap<String, String>
    private lateinit var app: HujiRideApplication


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_group, container, false)
        val groupsRecycler: RecyclerView = view.findViewById(R.id.search_recycler)
        val editText = view.findViewById<EditText>(R.id.search_group_editText)
        app = HujiRideApplication.getInstance()
        neighborhoods = app.jerusalemNeighborhoods
        app.groupsData.setFiltered(neighborhoods)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                filter(editable.toString())
            }
        })


        val clientId = app.userDetails.clientUniqueID
        adapter = SearchGroupAdapter()

        GlobalScope.launch(Dispatchers.IO) {

            val groups = app.db.getGroupsOfClient(clientId)
            adapter.setGroupsList(groups)
            adapter.notifyDataSetChanged()
            withContext(Dispatchers.Main) {
                groupsRecycler.adapter = adapter
                groupsRecycler.layoutManager =
                    LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

            }


        }


        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filter(text: String) {
        val filteredList = ArrayList<Pair<String, String>>()
        for (groupId in neighborhoods.keys) {
            val groupsName = neighborhoods[groupId]
            if (groupsName!!.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                filteredList.add(Pair(groupId, groupsName))
            }
        }
        app.groupsData.setFiltered(filteredList)
        adapter.notifyDataSetChanged()
    }
}