package huji.postpc.year2021.hujiride.ToolBarFraments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import huji.postpc.year2021.hujiride.R
import huji.postpc.year2021.hujiride.BugDialog


class BugsReport: Fragment() {

    private lateinit var usersReport: TextView
    private lateinit var addBug: FloatingActionButton
    private lateinit var thanksTxt: TextView
    private lateinit var thanksImg: ImageView



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bugs_report, container, false)
        addBug = view.findViewById(R.id.add_bug_btn)
        thanksTxt = view.findViewById(R.id.thanks)
        thanksImg = view.findViewById(R.id.thanks_img)
        thanksTxt.visibility = View.INVISIBLE
        thanksImg.visibility = View.INVISIBLE


        addBug.setOnClickListener{
            openDialog()
        }

        return view
    }

    fun makeVisible(){
        thanksTxt.visibility = View.VISIBLE
        thanksImg.visibility = View.VISIBLE
    }


    fun openDialog(){

        val dialog = BugDialog()
        dialog.onReportCallback = { makeVisible() }
        dialog.show(activity?.supportFragmentManager!!, "bug dialog")

    }


}