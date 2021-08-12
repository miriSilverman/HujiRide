package huji.postpc.year2021.hujiride

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {

    private val SHARED = "shared"
    private val DONE_ONBOARDING = "done onboarding"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sp: SharedPreferences = this.getSharedPreferences(SHARED, Context.MODE_PRIVATE)

        val doneOnboarding = sp.getBoolean(DONE_ONBOARDING, false)

        if (doneOnboarding) {
            setContentView(R.layout.home)
//            homeButtons()
        }
            else {
            onboardingCase(sp)
        }



    }


    private fun homeButtons(view : View)
    {
        val groupBtn = findViewById<TextView>(R.id.groups_btn)
        val searchBtn = findViewById<TextView>(R.id.search_btn)

        groupBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_log_1_to_log_2)

        }
    }





        private fun onboardingCase(sp: SharedPreferences) {

        val sharedVM = ViewModelProvider(this).get(OnBoardingVM::class.java)

        sharedVM.doneArr.observe(this, Observer { arr ->

            var num = 0
            for (task in arr) {
                if (task) num++
            }
            findViewById<ProgressBar>(R.id.progressBar).progress = num
        })


        sharedVM.doneOnBoard.observe(this, Observer { b ->
            if (b) {
                setContentView(R.layout.home)
                val editor: SharedPreferences.Editor = sp.edit()
                editor.putBoolean(DONE_ONBOARDING, true)
                editor.apply()
            }
        })
    }


}