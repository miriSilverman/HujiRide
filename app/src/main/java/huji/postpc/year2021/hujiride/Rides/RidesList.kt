package huji.postpc.year2021.hujiride.Rides

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import huji.postpc.year2021.hujiride.Groups.GroupsPickerDialog
import huji.postpc.year2021.hujiride.R
import huji.postpc.year2021.hujiride.HujiRideApplication
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import huji.postpc.year2021.hujiride.database.Ride


/**
 * list of the closest rides
 */
class RidesList : Fragment() {

    private lateinit var aView: View
    private lateinit var img: ImageView
    private lateinit var noRidesTxt: TextView

    private lateinit var sortTIL: TextInputLayout
    private lateinit var sortACTV: AutoCompleteTextView

    private lateinit var filterTIL: TextInputLayout
    private lateinit var filterACTV: AutoCompleteTextView

    private lateinit var adapter: RidesAdapter
    private lateinit var vm: RidesViewModel
    private lateinit var app: HujiRideApplication
    private lateinit var progressBar: ProgressBar
    private lateinit var addRideBtn: FloatingActionButton
    private lateinit var ridesRecycler: RecyclerView
    private var ridesList: ArrayList<Ride> = arrayListOf()
    private var newList = ArrayList<Ride>()
    private var dbRidesArr = ArrayList<Ride>()

    private lateinit var groupsBtn: FloatingActionButton
    private var groupsList: ArrayList<String> = arrayListOf()
    private lateinit var clientId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    private fun getGroupsName(id: String): String? {
        return app.jerusalemNeighborhoods[id]
    }

    private fun getIdOfGroup(name: String): String? {
        for (g in app.jerusalemNeighborhoods) {
            if (g.value == name) {
                return g.key
            }
        }
        return null
    }


    override fun onResume() {
        super.onResume()
        val sortItems = resources.getStringArray(R.array.sorting_list)
        val sortingArrayAdapter = ArrayAdapter(requireContext(), R.layout.sort_item, sortItems)
        sortACTV.setAdapter(sortingArrayAdapter)

        val filteredItems = resources.getStringArray(R.array.filtering_list)
        val filterArrayAdapter = ArrayAdapter(requireContext(), R.layout.sort_item, filteredItems)
        filterACTV.setAdapter(filterArrayAdapter)

        if (groupsList.isEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                val groupsListAsNums = app.db.getGroupsOfClient(clientId)
                withContext(Dispatchers.Main) {
                    for (g in groupsListAsNums) {
                        val groupsName = getGroupsName(g)
                        if (groupsName != null) {
                            groupsList.add(groupsName)
                        }
                    }
                    groupsList.add(GroupsPickerDialog.NOT_FROM_GROUP)
                }
            }

        }

    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        aView = inflater.inflate(R.layout.fragment_rides_list, container, false)
        app = HujiRideApplication.getInstance()
        clientId = app.userDetails.clientUniqueID

        findViews()


        addRideBtn.setOnClickListener {
            Navigation.findNavController(aView).navigate(R.id.action_ridesList_to_newRide2)
        }
        setVisibility(View.INVISIBLE, View.INVISIBLE, false, View.VISIBLE)

        adapter = RidesAdapter()


        vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)

        ridesRecycler.addItemDecoration(
            DividerItemDecoration(
                ridesRecycler.context,
                DividerItemDecoration.VERTICAL
            )
        )
        ridesRecycler.adapter = adapter
        ridesRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)


        adapter.onItemClickCallback = { ride: Ride ->
            vm.pressedRide.value = ride
            Navigation.findNavController(aView).navigate(R.id.action_ridesList_to_ridesDetails)
        }

        sortACTV.setOnItemClickListener { parent, _, pos, _ ->
            sortingSwitchCase()
            adapter.setRidesList(ridesList)
            adapter.notifyDataSetChanged()
        }


        filterACTV.setOnItemClickListener { parent, _, pos, _ ->
            filterSwitchCase()
            adapter.setRidesList(ridesList)
            adapter.notifyDataSetChanged()
        }

        setAdaptersList()

        groupsBtn.setOnClickListener {
            createGroupDialog()
        }

        return aView
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortAndFilter() {
        filterSwitchCase()
        adapter.setRidesList(ridesList)
        adapter.notifyDataSetChanged()
    }

    private fun filterSwitchCase() {
        when (filterACTV.text.toString()) {
            "Source to Huji" -> filterToHuji(true)
            "Huji to Destination" -> filterToHuji(false)
            "All" -> filterAll()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAdaptersList() {
        val curGroup = vm.pressedGroup
        val group = curGroup.value?.name
        var groupsName: String? = null
        if (group != null) {
            groupsName = group.toString()
        }

        GlobalScope.launch(Dispatchers.IO) {
            dbRidesArr = app.db.getRidesListOfGroup(groupsName)
            newList.clear()
            newList.addAll(dbRidesArr)

            ridesList.clear()
            ridesList.addAll(dbRidesArr)
            adapter.setRidesList(ridesList)

            withContext(Dispatchers.Main) {
                if (adapter.itemCount == 0) {
                    noNearRidesCase()
                } else {
                    thereAreRidesCase()
                }
                sortAndFilter()

            }

        }


    }


    private fun sortingSwitchCase() {
        when (sortACTV.text.toString()) {
            "No Sorting" -> noSorting()
            "Descending Time" -> sortAccordingToTime(false)
            "Ascending Time" -> sortAccordingToTime(true)
//            "src and dest" -> sortAccordingToAlloc()
            "Lexicographic" -> sortAccordingToLexicographic()
        }
    }

    private fun noSorting() {
//        ridesList.clear()
//        ridesList.addAll(dbRidesArr)
    }

    private fun createGroupDialog() {
        val builder = AlertDialog.Builder(requireContext())
//        val ss = groupsList.map { s -> s.subSequence(0, s.length) }.toTypedArray()
//        builder.setTitle("Choose Group")
//            .setSingleChoiceItems(ss, groupsList.indexOf(getGroupsName(vm.pressedGroup.value?.name?:NOT_FROM_GROUP)?:NOT_FROM_GROUP)) { dialog, which ->
//                val groupsName = groupsList[which]
//                if (groupsName != NOT_FROM_GROUP) {
//                    val groupsId = getIdOfGroup(groupsName)
//                    vm.pressedGroup.value = SearchGroupItem(groupsId, true)
//
//                } else {
//                    vm.pressedGroup.value = SearchGroupItem(null, true)
//                }
//
//                setAdaptersList()
//                dialog.dismiss()
//            }
//        builder.create().show()
        val dialog = GroupsPickerDialog(groupsList, vm.pressedGroup.value!!.name?:""){ sel ->
            vm.pressedGroup.value = SearchGroupItem(sel, true)
            setAdaptersList()
        }
        dialog.show(parentFragmentManager, "Group Select")

    }

    private fun sortAccordingToTime(ascending: Boolean) {
        val selector = {ride: Ride -> ride.timeStamp.toDate()}
        if (ascending)
            ridesList.sortBy { selector(it) }
        else
            ridesList.sortByDescending { selector(it) }
    }

    private fun sortAccordingToLexicographic() {
        ridesList.sortBy { ride -> ride.destName }
    }


    private fun filterToHuji(condition: Boolean) {
        ridesList.clear()
        ridesList.addAll(dbRidesArr.filter { ride -> ride.isDestinationHuji == condition })
        sortingSwitchCase()
    }

    private fun filterAll() {
        ridesList.clear()
        ridesList.addAll(dbRidesArr)
        sortingSwitchCase()
    }


    private fun findViews() {
        sortACTV = aView.findViewById(R.id.autoCompleteTextView2)
        filterACTV = aView.findViewById(R.id.autoCompleteFilter)
        addRideBtn = aView.findViewById(R.id.add_new_ride)
        img = aView.findViewById(R.id.no_rides_img)
        noRidesTxt = aView.findViewById(R.id.no_near_rides_txt)
        sortTIL = aView.findViewById(R.id.sort_as)
        filterTIL = aView.findViewById(R.id.filter)
        progressBar = aView.findViewById(R.id.rides_progress_bar)
        ridesRecycler = aView.findViewById(R.id.rides_list_recyclerView)

        groupsBtn = aView.findViewById(R.id.groups_btn)
    }

    private fun setVisibility(
        oneDirection: Int,
        secondDirection: Int,
        btnState: Boolean,
        progressbarVis: Int
    ) {
        addRideBtn.isEnabled = btnState

        progressBar.visibility = progressbarVis

        noRidesTxt.visibility = oneDirection
        img.visibility = oneDirection

        ridesRecycler.visibility = secondDirection
        sortTIL.visibility = secondDirection
        filterTIL.visibility = secondDirection
    }


    private fun noNearRidesCase() {
        setVisibility(View.VISIBLE, View.INVISIBLE, true, View.INVISIBLE)
    }

    private fun thereAreRidesCase() {
        setVisibility(View.INVISIBLE, View.VISIBLE, true, View.INVISIBLE)
    }

}






