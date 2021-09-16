package huji.postpc.year2021.hujiride.Onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            if (onClickNext()) {
                viewModel.progress.value = viewModel.progress.value!!+1
                nextPage()
            }
        }
        activity.setOnClickBack {
            if (onClickBack()) {
                viewModel.progress.value = viewModel.progress.value!!-1
                prevPage()
            }
        }
        return inflater.inflate(layoutID, container, false)
    }


    abstract fun onClickNext() : Boolean

    abstract fun onClickBack() : Boolean

    private fun nextPage() {
        Navigation.findNavController(requireView()).navigate(nextNavigationActionID)
    }

    protected fun prevPage() {
        Navigation.findNavController(requireView()).navigate(prevNavigationActionID)
    }

}