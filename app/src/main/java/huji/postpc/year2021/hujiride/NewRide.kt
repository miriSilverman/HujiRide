package huji.postpc.year2021.hujiride

import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.rpc.context.AttributeContext
import huji.postpc.year2021.hujiride.Rides.Ride
import huji.postpc.year2021.hujiride.Rides.RidesViewModel
import java.util.*


/**
 * create new ride as driver
 */
class NewRide : Fragment() {

    private lateinit var aView: View
    private lateinit var timerTextView: TextView
    private var timeHour: Int = 0
    private var timeMinutes: Int = 0
    private lateinit var srcET: EditText
    private lateinit var destET: EditText
    private lateinit var stops: AutoCompleteTextView
    private lateinit var comments: AutoCompleteTextView

    private lateinit var srcDestImg: ImageView
    private lateinit var switchDirectionBtn: Button
    private var toHuji: Boolean = true
    private lateinit var vm: RidesViewModel
    private lateinit var app: HujiRideApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun onResume() {
        super.onResume()
        val stopsSortItems = resources.getStringArray(R.array.stops_list)
        val stopsArrayAdapter = ArrayAdapter(requireContext(), R.layout.sort_item, stopsSortItems)
        aView.findViewById<AutoCompleteTextView>(R.id.autoCompleteStops)
            ?.setAdapter(stopsArrayAdapter)


        val commentsSortItems = resources.getStringArray(R.array.comments_list)
        val commentsArrayAdapter =
            ArrayAdapter(requireContext(), R.layout.sort_item, commentsSortItems)
        aView.findViewById<AutoCompleteTextView>(R.id.autoCompleteComments)
            ?.setAdapter(commentsArrayAdapter)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        aView = inflater.inflate(R.layout.fragment_new_ride, container, false)
        vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)

        srcET = aView.findViewById(R.id.source_edit_text)
        destET = aView.findViewById(R.id.dest_edit_text)
        stops = aView.findViewById(R.id.autoCompleteStops)
        comments = aView.findViewById(R.id.autoCompleteComments)
        timerTextView = aView.findViewById(R.id.time_edit_btn)
        srcDestImg = aView.findViewById(R.id.srcDestImg)
        switchDirectionBtn = aView.findViewById(R.id.switchDirectionBtn)
        app = HujiRideApplication.getInstance()

        setSrcOrDest()


        switchDirectionBtn.setOnClickListener {
            setDetails()
            setDirection()
        }

        timerTextView.setOnClickListener {
            timeDialog()
        }


        aView.findViewById<ImageView>(R.id.done_btn)?.setOnClickListener {
            val newRide: Ride = createNewRide(app)
            val pressedGroup = vm.pressedGroup
            app.ridesPerGroup.addRide(newRide, pressedGroup.value?.name!!)
            vm.srcOrDest = ""
            Navigation.findNavController(aView).navigate(R.id.action_newRide2_to_dashboard)
        }




        return aView
    }

    private fun createNewRide(app: HujiRideApplication): Ride {
        val newRide: Ride = Ride(
            srcET.text.toString(),
            destET.text.toString(),
            "$timeHour : $timeMinutes",
            arrayListOf<String>(stops.text.toString()),
            arrayListOf<String>(comments.text.toString()),
            app.userDetails.userFirstName,
            app.userDetails.userLastName,
            app.userDetails.userPhoneNumber,
            UUID.randomUUID(),
            toHuji
        )
        return newRide
    }

    private fun timeDialog() {
        val timePickerDialog: TimePickerDialog =
            TimePickerDialog(
                activity, TimePickerDialog.OnTimeSetListener()
                { v, hourOfDay, minutes ->
                    timeHour = hourOfDay
                    timeMinutes = minutes
                    val calendar = Calendar.getInstance()
                    calendar.set(0, 0, 0, timeHour, timeMinutes)
    //                    timerTextView!!.setText(DateFormat("HH:MM aa", calendar))
                    timerTextView.text = "Leaving at $timeHour : $timeMinutes"
                }, 12, 0, false
            )

        timePickerDialog.updateTime(timeHour, timeMinutes)
        timePickerDialog.show()
    }


    private fun setDirection() {
        if (toHuji) {

            srcDestImg.setImageResource(R.drawable.resource_switch)
            destET.setText(getString(R.string.destHujiField))
            destET.setTextColor(Color.BLACK)
            destET.isEnabled = false
            srcET.isEnabled = true
            if (vm.srcOrDest != ""){
                srcET.setText(vm.srcOrDest)
            }else{

                srcET.text?.clear()
            }

        } else {
            srcDestImg.setImageResource(R.drawable.switchfromhuji)
            srcET.setText(getString(R.string.destHujiField))
            srcET.setTextColor(Color.BLACK)
            destET.isEnabled = true
            srcET.isEnabled = false

            if (vm.srcOrDest != ""){
                destET.setText(vm.srcOrDest)
            }else{

                destET.text?.clear()
            }


        }
    }


    private fun setSrcOrDest() {
        toHuji = vm.toHuji
        if (vm.srcOrDest != "") {
            setDirection()

            if (toHuji) {
                srcET.setText(vm.srcOrDest)

            } else {
                destET.setText(vm.srcOrDest)

            }

        }else{
            setDirection()
        }

    }

    private fun setDetails(){
        if (toHuji){
            if (srcET.text?.isEmpty() != true){
                vm.srcOrDest = srcET.text.toString()
            }else{
                vm.srcOrDest = ""
            }
        }else{
            if (destET.text?.isEmpty() != true){
                vm.srcOrDest = destET.text.toString()
            }else{
                vm.srcOrDest = ""
            }

        }
        toHuji = !toHuji
        vm.toHuji = toHuji



    }


}