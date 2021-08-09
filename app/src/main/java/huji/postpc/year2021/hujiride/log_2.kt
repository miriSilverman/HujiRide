package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation


/**
 * log - phone and id
 */
class log_2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_log_2, container, false)
        view.findViewById<ImageView>(R.id.next_btn).setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_log_2_to_scan) }
        view.findViewById<ImageView>(R.id.back_btn).setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_log_2_to_log_1) }
        return view
    }


}