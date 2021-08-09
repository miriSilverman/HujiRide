package huji.postpc.year2021.hujiride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
        val vm = ViewModelProvider(requireActivity()).get(OnBoardingVM::class.java)
        vm.unDoneTask(1)

        view.findViewById<ImageView>(R.id.next_btn).setOnClickListener {
            onNextBtn(vm, view)
        }
        return view
    }



    fun onNextBtn(vm: OnBoardingVM, view: View) : Boolean
    {

        if (check_all_fields_are_filled())
        {
            vm.doneTask(1)
            Navigation.findNavController(view).navigate(R.id.action_log_1_to_log_2)
        }

        return true
    }


    fun check_all_fields_are_filled() : Boolean
    {
        return true
    }


}