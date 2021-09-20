package huji.postpc.year2021.hujiride.Onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation

abstract class BaseOnbaordingFragment(
    val layoutID: Int,
    val nextNavigationActionID: Int,
    val prevNavigationActionID: Int
) : Fragment() {
    protected val viewModel : OnBoardingVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = requireActivity() as OnboradingActivity
        activity.setOnClickNext {
            handleClickNext()
        }
        activity.setOnClickBack {
            handleClickBack()
        }
        return inflater.inflate(layoutID, container, false)
    }

    protected fun setLastEditTextToNextPage(lastEditText: EditText) {
        lastEditText.setOnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_NEXT || id == EditorInfo.IME_ACTION_DONE) {
                handleClickNext()
            }
            return@setOnEditorActionListener true
        }
    }

    abstract fun onClickNext() : Boolean

    abstract fun onClickBack() : Boolean

    protected fun nextPage() {
        Navigation.findNavController(requireView()).navigate(nextNavigationActionID)
    }

    protected fun prevPage() {
        Navigation.findNavController(requireView()).navigate(prevNavigationActionID)
    }

    protected fun handleClickNext() {
        if (onClickNext()) {
            viewModel.progress.value = viewModel.progress.value!!+1
            nextPage()
        }
    }

    protected fun handleClickBack() {
        if (onClickBack()) {
            viewModel.progress.value = viewModel.progress.value!!-1
            prevPage()
        }
    }
}