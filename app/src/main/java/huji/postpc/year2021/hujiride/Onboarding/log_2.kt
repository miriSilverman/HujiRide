package huji.postpc.year2021.hujiride.Onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import huji.postpc.year2021.hujiride.HujiRideApplication
import huji.postpc.year2021.hujiride.R


/**
 * log - phone and id
 */
class log_2 : BaseOnbaordingFragment(R.layout.fragment_log_2, R.id.action_log_2_to_scan, R.id.action_log_2_to_log_1) {

    lateinit var phoneNumberView : EditText
    lateinit var idNumberView : EditText

    val phoneNumberInput : String
        get() = phoneNumberView.text!!.toString()

    val idNumberInput : String
        get() = idNumberView.text!!.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)!!

        phoneNumberView = view.findViewById(R.id.phone_number_edittext)
        idNumberView = view.findViewById(R.id.id_number_edittext)

        return view
    }

    override fun onClickNext() {
        val phoneNumber = phoneNumberInput
        val idNumber = idNumberInput
        if (!validatePhoneNumber(phoneNumber)) {
            alertInvalidPhoneNumber()
            return
        }
        if (!validateID(idNumber)) {
            alertInvalidID()
            return
        }

        //TODO: MIRIS
        HujiRideApplication.getInstance().userDetails.userPhoneNumber = phoneNumberView.text.toString()

        viewModel.phoneNumber = phoneNumber
        viewModel.idNumber = idNumber
    }

    override fun onClickBack() {

    }

    private fun alertInvalidPhoneNumber() {

    }

    private fun alertInvalidID() {

    }

    fun validateID(ID: String) : Boolean {
        return validateIsraeliID(ID)
    }

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        val regex = Regex("^0(5[^7]|7[0-9])[0-9]{7}\$")
        return phoneNumber.matches(regex)
    }


}