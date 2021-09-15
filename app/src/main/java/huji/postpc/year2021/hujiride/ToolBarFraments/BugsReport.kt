package huji.postpc.year2021.hujiride.ToolBarFraments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import huji.postpc.year2021.hujiride.R

class BugsReport: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bugs_report, container, false)
    }


}