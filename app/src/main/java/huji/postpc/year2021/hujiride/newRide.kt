package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation


/**
 * create new ride as driver
 */
class newRide : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_new_ride, container, false)
        view.findViewById<ImageView>(R.id.done_btn).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_newRide2_to_dashboard)
        }
        return view
    }
}