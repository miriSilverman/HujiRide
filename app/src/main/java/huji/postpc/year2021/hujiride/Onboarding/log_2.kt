package huji.postpc.year2021.hujiride.Onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import huji.postpc.year2021.hujiride.HujiRideApplication
import huji.postpc.year2021.hujiride.R


/**
 * log - phone and id
 */
class log_2 : Fragment() {

    private var phoneNumber: EditText? = null


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
        val vm = ViewModelProvider(requireActivity()).get(OnBoardingVM::class.java)
        vm.unDoneTask(2)

        view.findViewById<ImageView>(R.id.next_btn).setOnClickListener {
            onNextBtn(vm, view)
        }
        view.findViewById<ImageView>(R.id.back_btn).setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_log_2_to_log_1) }
        phoneNumber = view.findViewById<EditText>(R.id.up_edit_txt)

        return view
    }


    fun onNextBtn(vm: OnBoardingVM, view: View) : Boolean
    {

        if (check_all_fields_are_filled())
        {
            HujiRideApplication.getInstance().userDetails.userPhoneNumber = phoneNumber?.text.toString()
            vm.doneTask(2)
            Navigation.findNavController(view).navigate(R.id.action_log_2_to_scan)
        }

        return true
    }


    fun check_all_fields_are_filled() : Boolean
    {
        return true
    }


}