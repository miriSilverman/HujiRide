package huji.postpc.year2021.hujiride.Onboarding

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import huji.postpc.year2021.hujiride.R
import huji.postpc.year2021.hujiride.HujiRideApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Successful_log : BaseOnbaordingFragment(R.layout.fragment_successful_log, -1, R.id.action_successful_log_to_log1) {

    private lateinit var app: HujiRideApplication
    private lateinit var loadingBar: ProgressBar
    private lateinit var cancelRegDialog: AlertDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)!!
        loadingBar = view.findViewById(R.id.complete_reg_loading)
        loadingBar.visibility = View.INVISIBLE

        cancelRegDialog = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.cancel_reg_dialog_body))
            .setTitle(getString(R.string.cancel_reg_dialog_title))
            .setPositiveButton(getString(R.string.yes)) { dialog, id ->
                viewModel.progress.value = 0
                viewModel.resetData()
                prevPage()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, id ->

            }
            .create()
        app = requireActivity().application as HujiRideApplication
        return view
    }

    override fun onClickNext(): Boolean {
        val db = (requireActivity().application as HujiRideApplication).db
        loadingBar.visibility = View.VISIBLE
        println("$%%$%$%$%%$")

        GlobalScope.launch (Dispatchers.IO) {
            val e = db.newClient(
                firstName = viewModel.firstName!!,
                lastName = viewModel.lastName!!,
                phoneNumber = viewModel.phoneNumber!!,
                uniqueID = app.userDetails.clientUniqueID
            )
            withContext(Dispatchers.Main) {
                loadingBar.visibility = View.INVISIBLE
                println("################$e")
            }
        }
        return false
    }

    override fun onClickBack(): Boolean {
        throwCancelRegDialog()
        return false
    }

    private fun throwCancelRegDialog() {
        cancelRegDialog.show()
    }

}