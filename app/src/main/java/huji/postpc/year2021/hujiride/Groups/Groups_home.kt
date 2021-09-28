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
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
    private lateinit var progressBar: ProgressBar
    private lateinit var aView: View
    private lateinit var searchNewGroupBtn: FloatingActionButton
    private lateinit var groupRecyclerView: RecyclerView
    private lateinit var adapter: GroupsAdapter
    private lateinit var editCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    private fun findViews() {
        progressBar = aView.findViewById(R.id.groups_progress_bar)
        searchNewGroupBtn = aView.findViewById(R.id.search_new_group_btn)
        groupRecyclerView = aView.findViewById(R.id.groups_list_recyclerView)
        editCheckBox = aView.findViewById(R.id.editCheckbox)
    }

    private fun setVisibility(oneDirection: Int, secondDirection: Int, btnState: Boolean) {
        progressBar.visibility = oneDirection
        groupRecyclerView.visibility = secondDirection
        searchNewGroupBtn.isEnabled = btnState
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aView = inflater.inflate(R.layout.fragment_groups_home, container, false)
        findViews()
        setVisibility(View.VISIBLE, View.INVISIBLE, false)

        app = HujiRideApplication.getInstance()
        val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
        vm.fromMyRides = false
        vm.fromDashboard = false
        vm.latLng = null
        val clientId = app.userDetails.clientUniqueID
        searchNewGroupBtn.setOnClickListener {
            Navigation.findNavController(aView).navigate(R.id.action_groups_home_to_searchGroup)
        }




        adapter = GroupsAdapter()
        adapter.setContext(requireActivity())
        val groupsRecycler: RecyclerView = aView.findViewById(R.id.groups_list_recyclerView)
        groupsRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        GlobalScope.launch(Dispatchers.IO) {
            val groupsList = app.db.getGroupsOfClient(clientId)
            withContext(Dispatchers.Main) {
                adapter.setGroupsList(groupsList)
                setVisibility(View.INVISIBLE, View.VISIBLE, true)
                adapter.notifyDataSetChanged()

                groupsRecycler.adapter = adapter


                adapter.onItemClickCallback = { group: String ->
                    vm.pressedGroup.value = SearchGroupItem(group, true)
                    Navigation.findNavController(aView)
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

        editCheckBox.setOnClickListener {
            adapter.setEdit(editCheckBox.isChecked)
            groupsRecycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }



        return aView
    }




}



