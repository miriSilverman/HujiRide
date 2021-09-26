package huji.postpc.year2021.hujiride

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BugDialog: AppCompatDialogFragment() {
    private lateinit var comment : EditText
    var onReportCallback: (()->Unit)? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        val inflater = activity?.layoutInflater

        val aView = inflater?.inflate(R.layout.bug_dialog, null)

        comment = aView!!.findViewById(R.id.bug_comment_et)


        builder.setView(aView)
            .setTitle("Report New Bug")
            .setNegativeButton("cancel") { _: DialogInterface, _: Int -> }
            .setPositiveButton("report") { _: DialogInterface, _: Int ->

                if (comment.text.isNotEmpty()) {

                    GlobalScope.launch(Dispatchers.IO) {
                        HujiRideApplication.getInstance().db.addBug(comment.text.toString())
                        withContext(Dispatchers.Main) {
                            onReportCallback?.invoke()
                        }
                    }
                }

            }


        return builder.create()


    }



}