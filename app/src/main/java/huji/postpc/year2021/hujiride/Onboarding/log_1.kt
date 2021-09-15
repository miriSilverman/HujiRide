package huji.postpc.year2021.hujiride.Onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import huji.postpc.year2021.hujiride.HujiRideApplication
import huji.postpc.year2021.hujiride.R


/**
 * log - first and last name
 */
class log_1 : BaseOnbaordingFragment(R.layout.fragment_log_1, R.id.action_log_1_to_log_2, -1) {
    private var firstNameView: EditText? = null
    private var lastNameView: EditText? = null

    private val firstNameInput : String
        get() = firstNameView!!.text.toString()

    private val lastNameInput : String
        get() = lastNameView!!.text.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)!!
        firstNameView = view.findViewById(R.id.up_edit_txt)
        lastNameView = view.findViewById(R.id.lower_edit_txt)
        (requireActivity() as OnboradingActivity).setOnClickBack(null)
        return view
    }

    override fun onClickNext() {
        val firstName = firstNameInput
        val lastName = lastNameInput
//        if (!validateFirstName(firstName)) {
//            alertInvalidFirstName()
//            return
//        }
//        if (!validateLastName(lastName)) {
//            alertInvalidLastName()
//            return
//        }


        // TODO: The next two lines needs to be deleted! MIRI ASKED FOR IT TO STAY!!!!
        HujiRideApplication.getInstance().userDetails.userFirstName = firstNameView?.text.toString()
        HujiRideApplication.getInstance().userDetails.userLastName = lastNameView?.text.toString()


        viewModel.firstName = firstName
        viewModel.lastName = lastName
    }

    override fun onClickBack() {
        // Shouldn't do a thing!
    }


    private fun alertInvalidFirstName() {
        Toast.makeText(requireContext(), "Invalid First Name! try again!", Toast.LENGTH_LONG).show()
    }

    private fun alertInvalidLastName() {
        Toast.makeText(requireContext(), "Invalid Last Name! try again!", Toast.LENGTH_LONG).show()
    }

    private fun validateFirstName(s: String) : Boolean {
        val regex = Regex("[A-Za-z]{2,9}|[א-ת]")
        return s.matches(regex)
    }

    private fun validateLastName(s: String) : Boolean {
        val regex = Regex("[A-Za-z]{2,9}|[א-ת]")
        return s.matches(regex)
    }
}