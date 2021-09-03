package huji.postpc.year2021.hujiride

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import java.lang.String.format
import java.text.DateFormat
import java.text.MessageFormat.format
import java.util.*


/**
 * create new ride as driver
 */
class newRide : Fragment() {

    private var aView: View? = null
    private var timerTextView: TextView? = null
    private var timeHour: Int = 0
    private var timeMinutes: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun onResume() {
        super.onResume()
        val stopsSortItems = resources.getStringArray(R.array.stops_list)
        val stopsArrayAdapter = ArrayAdapter(requireContext(), R.layout.sort_item, stopsSortItems)
        aView?.findViewById<AutoCompleteTextView>(R.id.autoCompleteStops)?.setAdapter(stopsArrayAdapter)


        val commentsSortItems = resources.getStringArray(R.array.comments_list)
        val commentsArrayAdapter = ArrayAdapter(requireContext(), R.layout.sort_item, commentsSortItems)
        aView?.findViewById<AutoCompleteTextView>(R.id.autoCompleteComments)?.setAdapter(commentsArrayAdapter)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        aView =  inflater.inflate(R.layout.fragment_new_ride, container, false)
        aView?.findViewById<ImageView>(R.id.done_btn)?.setOnClickListener {
            Navigation.findNavController(aView!!).navigate(R.id.action_newRide2_to_dashboard)
        }



        timerTextView = aView?.findViewById(R.id.time_edit_btn)


        timerTextView?.setOnClickListener {


            var timePickerDialog : TimePickerDialog = TimePickerDialog(activity, TimePickerDialog.OnTimeSetListener()
            {
                v, hourOfDay, minutes ->
                    timeHour = hourOfDay
                    timeMinutes = minutes
                    val calendar = Calendar.getInstance()
                    calendar.set(0,0,0,timeHour, timeMinutes)
//                    timerTextView!!.setText(DateFormat("HH:MM aa", calendar))
                    timerTextView!!.setText("Leaving at $timeHour : $timeMinutes")
            }, 12, 0, false)

            timePickerDialog.updateTime(timeHour, timeMinutes)
            timePickerDialog.show()

        }


        return aView
    }
}