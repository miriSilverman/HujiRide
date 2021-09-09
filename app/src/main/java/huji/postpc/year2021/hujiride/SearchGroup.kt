package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.Groups.Group


/**
 * search for new group
 */
class SearchGroup : Fragment() {

    private val neighborhoods: List<SearchGroupItem> = arrayListOf(
        SearchGroupItem("Malcha"), SearchGroupItem("Bakaa"), SearchGroupItem("Talpiyot"),
        SearchGroupItem("Pisga zeev"), SearchGroupItem("Gilo"))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_search_group, container, false)

        val adapter = SearchGroupAdapter()
        adapter.setGroupsList(neighborhoods)

        val groupsRecycler: RecyclerView = view.findViewById(R.id.search_recycler)
        groupsRecycler.adapter = adapter
        groupsRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)


//        adapter.onItemClickCallback = {group: SearchGroupItem ->
//            Navigation.findNavController(view).navigate(R.id.action_groups_home_to_ridesList)
//        }


        return view
    }

}