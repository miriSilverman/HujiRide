package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import org.w3c.dom.Text


/**
 * log - first and last name
 */
class log_1 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_log_1, container, false)
        view.findViewById<ImageView>(R.id.next_btn).setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_log_1_to_log_2) }
        return view
    }


}