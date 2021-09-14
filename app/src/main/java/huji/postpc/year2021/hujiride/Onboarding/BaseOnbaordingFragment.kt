package huji.postpc.year2021.hujiride.Onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation


/**
 * A simple [Fragment] subclass.
 * Use the [BaseOnbaordingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
            onClickNext()
            viewModel.progress.value = viewModel.progress.value!!+1
            nextPage()
        }
        activity.setOnClickBack {
            onClickBack()
            viewModel.progress.value = viewModel.progress.value!!-1
            prevPage()
        }
        return inflater.inflate(layoutID, container, false)
    }


    abstract fun onClickNext()

    abstract fun onClickBack()

    fun nextPage() {
        Navigation.findNavController(requireView()).navigate(nextNavigationActionID)
    }

    private fun prevPage() {
        Navigation.findNavController(requireView()).navigate(prevNavigationActionID)
    }

}