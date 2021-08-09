package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation

/**
 * log - scan students card
 */
class scan : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scan, container, false)
        view.findViewById<ImageView>(R.id.next_btn).setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_scan_to_successful_log) }
        view.findViewById<ImageView>(R.id.back_btn).setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_scan_to_log_2) }
        return view
    }


}