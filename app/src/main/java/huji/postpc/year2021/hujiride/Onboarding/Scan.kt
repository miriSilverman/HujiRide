package huji.postpc.year2021.hujiride.Onboarding

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

import androidx.core.app.ActivityCompat.startActivityForResult
import java.lang.reflect.Array.newInstance
import java.net.URLClassLoader.newInstance
import javax.xml.datatype.DatatypeFactory.newInstance
import huji.postpc.year2021.hujiride.MainActivity


/**
 * log - scan students card
 */
class Scan : BaseOnbaordingFragment(
    R.layout.fragment_scan,
    R.id.action_scan_to_successful_log,
    R.id.action_scan_to_log_2
) {

    private lateinit var img_but: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val imm =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        if (view != null) {
            img_but = view.findViewById(R.id.camera)
            img_but.setOnClickListener {
                startActivityForResult(
                    Intent(
                        activity,
                        CameraActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP), 1
                )

            }
        }


        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            Log.d("check id", "YES")
        } else {
            Log.d("check id", "NO")

        }
    }


    override fun onClickNext(): Boolean {

        return true
    }

    override fun onClickBack(): Boolean {
        return true
    }


}