package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import huji.postpc.year2021.hujiride.Groups.Group


/**
 * A simple [Fragment] subclass.
 */
class SearchNewGroup : Fragment() {

    private var searchView: SearchView? = null
    private var listView: ListView? = null

    private val neighborhoods: List<String> = arrayListOf("Malcha", "Bakaa", "Talpiyot", "Pisga zeev", "Gilo")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_new_group, container, false)

        view.findViewById<Button>(R.id.add_group_btn).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_searchNewGroup_to_groups_home2)
        }


        listView = view.findViewById(R.id.groups_search_list)
        searchView = view.findViewById(R.id.search_group_edittext)


        var arrayAdapter = activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, android.R.id.text1, neighborhoods) }
        listView?.setAdapter(arrayAdapter)

        listView?.setOnItemClickListener(){ parent, view, position, id ->
//            Toast.makeText(activity, "you clicked on "+parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
            val app = HujiRideApplication.getInstance()
            app.groupsData.addGroup(Group(parent.getItemAtPosition(position).toString()))
        }

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                arrayAdapter?.getFilter()?.filter(p0)
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                if (arrayAdapter != null) {
                    arrayAdapter.getFilter().filter(p0)
                }

                return false
            }
        })

        return view
    }

}