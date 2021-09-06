package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection


/**
 * A simple [Fragment] subclass.
 */
class groups_home : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_groups_home, container, false)


        view.findViewById<Button>(R.id.search_new_group_btn).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_groups_home_to_searchNewGroup)
        }

        val app = HujiRideApplication.getInstance()


        val groupsData = app.groupsData

        val adapter = GroupsAdapter()

        val groupsRecycler: RecyclerView = view.findViewById(R.id.groups_list_recyclerView)
        groupsRecycler.adapter = adapter
        groupsRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)


        adapter.onItemClickCallback = {group: Group->
            Navigation.findNavController(view).navigate(R.id.action_groups_home_to_ridesList)
        }

        adapter.onDeleteIconCallback = { group : Group ->
            groupsData.removeGroup(group)
        }

        activity?.let {
            groupsData.liveDataGroups.observe(it, { groupsList ->
                adapter.notifyDataSetChanged()
            })
        }

        return view
    }

}