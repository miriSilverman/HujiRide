package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation


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


        view.findViewById<Button>(R.id.group_example).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_groups_home_to_ridesList)
        }


        return view
    }

}