package huji.postpc.year2021.hujiride.Onboarding

import android.animation.ObjectAnimator
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import huji.postpc.year2021.hujiride.R
import java.util.*

class OnboradingActivity : AppCompatActivity() {
    private lateinit var nextBtn: View
    private lateinit var backBtn: View
    lateinit var progressBar: ProgressBar

    private val viewModel : OnBoardingVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleUniqueID()

        setContentView(R.layout.activity_onborading)

        nextBtn = findViewById(R.id.next_btn)
        backBtn = findViewById(R.id.back_btn)
        setOnClickNext { viewModel.onClickNext }
        setOnClickBack { viewModel.onClickBack }

        progressBar = findViewById(R.id.onbaording_progressbar)
        chooseFragment()
        viewModel.progress.observe(this, {progress ->
            setProgressbar(progress)
        })
    }

    private fun handleUniqueID() {
        val sp = getSharedPreferences("huji.rides.unique.id.sp", Context.MODE_PRIVATE)

        val spKey = "huji.rides.yair.unique.id.client"
        val uniqueID = sp.getString(spKey, null)
        if (uniqueID != null) {
            viewModel.clientUniqueID = uniqueID
            return
        }
        val newUniqueID = UUID.randomUUID().toString()
        sp.edit {
            putString(spKey, newUniqueID)
            viewModel.clientUniqueID = newUniqueID
        }

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
        viewModel.onClickNext = onClick
        nextBtn.apply{
            setOnClickListener { viewModel.onClickNext?.invoke() }
            isEnabled = onClick != null
        }
    }

    fun setOnClickBack(onClick: (() -> Unit)?) {
        viewModel.onClickBack = onClick
        backBtn.apply{
            setOnClickListener { viewModel.onClickBack?.invoke() }
            isEnabled = onClick != null
        }
    }

    private fun setProgressbar(progress: Int) {
        val animation: ObjectAnimator =
            ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, (progress + 1) * 100)
        with(animation) {
            duration = 500
            setAutoCancel(true)
            interpolator = DecelerateInterpolator()
            start()
        }

    }

    override fun onBackPressed() {
        if (viewModel.onClickBack != null) {
            viewModel.onClickBack!!.invoke()
        } else {
            super.onBackPressed()
        }
    }

}