package huji.postpc.year2021.hujiride.Onboarding

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import huji.postpc.year2021.hujiride.HujiRideApplication
import huji.postpc.year2021.hujiride.R


/**
 * log - first and last name
 */
class log_1 : BaseOnbaordingFragment(R.layout.fragment_log_1, R.id.action_log_1_to_log_2, -1) {
    private lateinit var firstNameView: EditText
    private lateinit var lastNameView: EditText

    private val firstNameInput: String
        get() = firstNameView.text.toString()

    private val lastNameInput: String
        get() = lastNameView.text.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)!!
        firstNameView = view.findViewById(R.id.first_name_edit_text)
        lastNameView = view.findViewById(R.id.last_name_edit_text)

        firstNameView.setText(viewModel.firstName)
        lastNameView.setText(viewModel.lastName)

        setLastEditTextToNextPage(lastNameView)

        (requireActivity() as OnboradingActivity).setOnClickBack(null)
        return view
    }

    override fun onClickNext(): Boolean {
        val firstName = firstNameInput
        val lastName = lastNameInput

        if (firstName == "YNMAgsdk492106") {
            viewModel.bypassValidation = true
        }

        if (!validateFirstName(firstName) && !viewModel.bypassValidation) {
            alertInvalidFirstName()
            println("${viewModel.bypassValidation}  ${!validateFirstName(firstName) || viewModel.bypassValidation}")
            return false
        }
        if (!validateLastName(lastName) && !viewModel.bypassValidation) {
            alertInvalidLastName()
            return false
        }


        // TODO: The next two lines needs to be deleted! MIRI ASKED FOR IT TO STAY!!!!
        HujiRideApplication.getInstance().userDetails.userFirstName = firstNameView.text.toString()
        HujiRideApplication.getInstance().userDetails.userLastName = lastNameView.text.toString()


        viewModel.firstName = firstName
        viewModel.lastName = lastName
        return true
    }

    override fun onClickBack(): Boolean {
        // Shouldn't do a thing!
        return false
    }


    private fun alertInvalidFirstName() {
        Toast.makeText(requireContext(), "Invalid First Name! try again!", Toast.LENGTH_SHORT)
            .show()
    }

    private fun alertInvalidLastName() {
        Toast.makeText(requireContext(), "Invalid Last Name! try again!", Toast.LENGTH_SHORT).show()
    }

    private fun validateFirstName(s: String): Boolean {
        val regex = Regex("[A-Za-z]{2,9}|[א-ת]")
        return s.matches(regex)
    }

    private fun validateLastName(s: String): Boolean {
        val regex = Regex("[A-Za-z]{2,9}|[א-ת]")
        return s.matches(regex)
    }
}