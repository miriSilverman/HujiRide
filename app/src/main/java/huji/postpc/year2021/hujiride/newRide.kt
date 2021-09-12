package huji.postpc.year2021.hujiride

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import huji.postpc.year2021.hujiride.Rides.Ride
import huji.postpc.year2021.hujiride.Rides.RidesViewModel
import java.lang.String.format
import java.text.DateFormat
import java.text.MessageFormat.format
import java.util.*
import kotlin.collections.ArrayList


/**
 * create new ride as driver
 */
class newRide : Fragment() {

    private var aView: View? = null
    private var timerTextView: TextView? = null
    private var timeHour: Int = 0
    private var timeMinutes: Int = 0
    private var srcET: EditText? = null
    private var destET: EditText? = null
    private var stops: AutoCompleteTextView? = null
    private var comments: AutoCompleteTextView? = null





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

        srcET = aView?.findViewById(R.id.source_edit_text)
        destET = aView?.findViewById(R.id.dest_edit_text)
        stops = aView?.findViewById(R.id.autoCompleteStops)
        comments = aView?.findViewById(R.id.autoCompleteComments)


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

        val app = HujiRideApplication.getInstance()

        aView?.findViewById<ImageView>(R.id.done_btn)?.setOnClickListener {


            val newRide: Ride = Ride(srcET?.text.toString(), destET?.text.toString(),
                    "$timeHour : $timeMinutes",
                    arrayListOf<String>(stops?.text.toString()), arrayListOf<String>(comments?.text.toString()),
                    app.userDetails.userFirstName, app.userDetails.userLastName,
                    app.userDetails.userPhoneNumber,
                    UUID.randomUUID())


            val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)

            val pressedGroup = vm.pressedGroup
            app.ridesPerGroup.addRide(newRide, pressedGroup.value?.name!!)
            Navigation.findNavController(aView!!).navigate(R.id.action_newRide2_to_dashboard)
        }




        return aView
    }
}