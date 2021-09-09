package huji.postpc.year2021.hujiride.Onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import huji.postpc.year2021.hujiride.R


/**
 * screen of successful log
 */
class successful_log : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_successful_log, container, false)
        val vm = ViewModelProvider(requireActivity()).get(OnBoardingVM::class.java)
        view.findViewById<Button>(R.id.keep_going).setOnClickListener {
            onNextBtn(vm, view)
        }
        return view
    }


    fun onNextBtn(vm: OnBoardingVM, view: View) : Boolean
    {
        vm.doneTask(4)
        return true
    }


}