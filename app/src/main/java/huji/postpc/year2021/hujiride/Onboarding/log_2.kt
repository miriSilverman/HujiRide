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
 * log - phone and id
 */
class log_2 : BaseOnbaordingFragment(R.layout.fragment_log_2, R.id.action_log_2_to_scan, R.id.action_log_2_to_log_1) {

    private lateinit var phoneNumberView : EditText
    private lateinit var idNumberView : EditText

    private val phoneNumberInput : String
        get() = phoneNumberView.text!!.toString()

    private val idNumberInput : String
        get() = idNumberView.text!!.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)!!

        phoneNumberView = view.findViewById(R.id.phone_number_edittext)
        idNumberView = view.findViewById(R.id.id_number_edittext)

        phoneNumberView.setText(viewModel.phoneNumber)
        idNumberView.setText(viewModel.idNumber)

        setLastEditTextToNextPage(idNumberView)

        return view
    }

    override fun onClickNext(): Boolean {
        val phoneNumber = phoneNumberInput
        val idNumber = idNumberInput
        if (!validatePhoneNumber(phoneNumber) && !viewModel.bypassValidation) {
            alertInvalidPhoneNumber()
            return false
        }
        if (!validateID(idNumber) && !viewModel.bypassValidation) {
            alertInvalidID()
            return false
        }

        //TODO: MIRIS
        HujiRideApplication.getInstance().userDetails.userPhoneNumber = phoneNumberView.text.toString()

        viewModel.phoneNumber = phoneNumber
        viewModel.idNumber = idNumber
        return true
    }

    override fun onClickBack() : Boolean {
        return true
    }

    private fun alertInvalidPhoneNumber() {
        Toast.makeText(requireContext(), "Invalid Phone Number! Please try again.", Toast.LENGTH_SHORT).show()
    }

    private fun alertInvalidID() {
        Toast.makeText(requireContext(), "Invalid ID! Please try again.", Toast.LENGTH_SHORT).show()
    }

    private fun validateID(ID: String) : Boolean {
        return validateIsraeliID(ID)
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        val regex = Regex("^0(5[^7]|7[0-9])[0-9]{7}\$")
        return phoneNumber.matches(regex)
    }


}