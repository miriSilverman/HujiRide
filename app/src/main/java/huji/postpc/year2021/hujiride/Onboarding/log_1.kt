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
 * log - first and last name
 */
class log_1 : Fragment() {

    private var firstName: EditText? = null
    private var lastName: EditText? = null


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

        firstName = view.findViewById<EditText>(R.id.up_edit_txt)
        lastName = view.findViewById<EditText>(R.id.lower_edit_txt)


        return view
    }



    fun onNextBtn(vm: OnBoardingVM, view: View) : Boolean
    {

        if (check_all_fields_are_filled())
        {
            HujiRideApplication.getInstance().userDetails.userFirstName = firstName?.text.toString()
            HujiRideApplication.getInstance().userDetails.userLastName = lastName?.text.toString()

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