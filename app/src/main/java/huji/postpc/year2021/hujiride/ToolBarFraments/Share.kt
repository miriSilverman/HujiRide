package huji.postpc.year2021.hujiride.ToolBarFraments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import huji.postpc.year2021.hujiride.R

class Share: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.share, container, false)

        val shareBtn = view.findViewById<Button>(R.id.share_btn)

        shareBtn.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            val body = "Download this app"
            val sub = "http://play.google.com" //todo: change to real link
            intent.putExtra(Intent.EXTRA_TEXT, body)
            intent.putExtra(Intent.EXTRA_TEXT, sub)
            startActivity(Intent.createChooser(intent, "Share using"))
        }


        return view
    }


}