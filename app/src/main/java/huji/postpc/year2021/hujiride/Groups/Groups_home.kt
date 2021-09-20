package huji.postpc.year2021.hujiride.Groups

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import huji.postpc.year2021.hujiride.HujiRideApplication
import huji.postpc.year2021.hujiride.R
import huji.postpc.year2021.hujiride.Rides.RidesViewModel
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass.
 */
class GroupsHome : Fragment() {
    private lateinit var app: HujiRideApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_groups_home, container, false)
        app = HujiRideApplication.getInstance()
        val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
        val clientId = app.userDetails.clientUniqueID
        view.findViewById<Button>(R.id.search_new_group_btn).setOnClickListener {


            Navigation.findNavController(view).navigate(R.id.action_groups_home_to_searchGroup)
        }


        val adapter = GroupsAdapter()
        val groupsRecycler: RecyclerView = view.findViewById(R.id.groups_list_recyclerView)
        groupsRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        GlobalScope.launch(Dispatchers.IO) {
            val groupsList = app.db.getGroupsOfClient(clientId)
            withContext(Dispatchers.Main) {
                adapter.setGroupsList(groupsList)
                adapter.notifyDataSetChanged()

                groupsRecycler.adapter = adapter


                adapter.onItemClickCallback = { group: String ->
                    vm.pressedGroup.value = SearchGroupItem(group, true)
                    Navigation.findNavController(view)
                        .navigate(R.id.action_groups_home_to_ridesList)
                }
                adapter.onDeleteIconCallback = { group: String ->
                    GlobalScope.launch(Dispatchers.IO) {
                        app.db.unregisterClientToGroup(clientId, group)
                        adapter.setGroupsList(app.db.getGroupsOfClient(clientId))
                        withContext(Dispatchers.Main) {
                            adapter.notifyDataSetChanged()
                        }

                    }
                }


            }

        }


//        activity?.let {
//            groupsData.liveDataGroups.observe(it, { groupsList ->
//                adapter.notifyDataSetChanged()
//            })
//        }

        return view
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