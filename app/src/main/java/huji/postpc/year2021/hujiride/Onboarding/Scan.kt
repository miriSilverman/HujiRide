package huji.postpc.year2021.hujiride.Onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import huji.postpc.year2021.hujiride.R

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
        val vm = ViewModelProvider(requireActivity()).get(OnBoardingVM::class.java)
        vm.unDoneTask(3)

        view.findViewById<ImageView>(R.id.next_btn).setOnClickListener {
            onNextBtn(vm, view)
        }
        view.findViewById<ImageView>(R.id.back_btn).setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_scan_to_log_2) }
        return view
    }


    fun onNextBtn(vm: OnBoardingVM, view: View) : Boolean
    {

        if (check_all_fields_are_filled())
        {
            vm.doneTask(3)
            Navigation.findNavController(view).navigate(R.id.action_scan_to_successful_log)
        }

        return true
    }


    fun check_all_fields_are_filled() : Boolean
    {
        return true
    }


}