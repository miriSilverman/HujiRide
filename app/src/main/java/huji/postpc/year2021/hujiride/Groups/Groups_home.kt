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
        val app = HujiRideApplication.getInstance()
        val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
        val groupsData = app.groupsData
        val clientId = app.userDetails.clientUniqueID
        view.findViewById<Button>(R.id.search_new_group_btn).setOnClickListener {

//            val builder = AlertDialog.Builder(activity)
//            builder.setTitle("Add groups")
//            builder.setMultiChoiceItems(groupsData.neighborhoods, groupsData.checkedItems,
//            DialogInterface.OnMultiChoiceClickListener({
//                dialogInterface, i, b ->
//
//            }))

            Navigation.findNavController(view).navigate(R.id.action_groups_home_to_searchGroup)
        }


        val adapter = GroupsAdapter()
        val groupsRecycler: RecyclerView = view.findViewById(R.id.groups_list_recyclerView)
        groupsRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        GlobalScope.launch(Dispatchers.IO) {
            val groupsList = app.db.getGroupsOfClient(clientId)
            withContext(Dispatchers.Main) {
                if (groupsList != null) {
                    adapter.setGroupsList(groupsList)
                }
                groupsRecycler.adapter = adapter


                adapter.onItemClickCallback = { group: String ->
                    vm.pressedGroup.value = SearchGroupItem(group, true)
                    Navigation.findNavController(view).navigate(R.id.action_groups_home_to_ridesList)
                }

                adapter.onDeleteIconCallback = { group: String ->
                    groupsData.removeGroup(group)
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

}