package huji.postpc.year2021.hujiride.Onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import huji.postpc.year2021.hujiride.R

class OnboradingActivity : AppCompatActivity() {
    private lateinit var nextBtn: View
    private lateinit var backBtn: View
    private lateinit var progressBar: ProgressBar

    private var onClickNext : (()->Unit)? = null
    private var onClickBack : (()->Unit)? = null
    private val viewModel : OnBoardingVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onborading)
        nextBtn = findViewById(R.id.next_btn)
        backBtn = findViewById(R.id.back_btn)
        progressBar = findViewById(R.id.onbaording_progressbar)
        chooseFragment()

        viewModel.progress.observe(this, {progress -> setProgressbar(progress)})

    }

    private fun chooseFragment() {
        val actionID = when (viewModel.progress.value) {
            0 -> R.id.action_global_log_1
            1 -> R.id.action_global_log_2
            2 -> R.id.action_global_scan
            3 -> R.id.action_global_successful_log
            else -> -1
        }
        val navHost = supportFragmentManager.findFragmentById(R.id.onborading_nav_fragment_container)
        val navController = navHost!!.findNavController()
        navController.navigate(actionID)
    }

    fun setOnClickNext(onClick: (()->Unit)?) {
        onClickNext = onClick
        nextBtn.apply{
            setOnClickListener { onClickNext?.invoke() }
            isEnabled = onClick != null
        }
    }

    fun setOnClickBack(onClick: (() -> Unit)?) {
        onClickBack = onClick
        backBtn.apply{
            setOnClickListener { onClickBack?.invoke() }
            isEnabled = onClick != null
        }
    }

    private fun setProgressbar(progress: Int) {
        progressBar.progress = progress + 1
    }

    override fun onBackPressed() {
        if (onClickBack != null) {
            onClickBack!!.invoke()
        } else {
            super.onBackPressed()
        }
    }

}