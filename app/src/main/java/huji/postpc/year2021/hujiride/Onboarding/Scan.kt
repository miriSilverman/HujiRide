package huji.postpc.year2021.hujiride.Onboarding

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.api.Context
import huji.postpc.year2021.hujiride.R

/**
 * log - scan students card
 */
class Scan : BaseOnbaordingFragment(R.layout.fragment_scan, R.id.action_scan_to_successful_log, R.id.action_scan_to_log_2) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val imm = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)


        return view
    }

    override fun onClickNext(): Boolean {
        return true
    }

    override fun onClickBack(): Boolean {
        return true
    }


}