package huji.postpc.year2021.hujiride

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
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
        app = HujiRideApplication.getInstance()

        findViews()
        setSrcOrDest()


        switchDirectionBtn.setOnClickListener {
            setDetails()
            setDirection()
        }

        timerTextView.setOnClickListener {
            timeDialog()
        }

        audioNotification()


        aView.findViewById<ImageView>(R.id.done_btn)?.setOnClickListener {
            onPressedAddNewRide()
        }

        return aView
    }

    private fun onPressedAddNewRide() {
        if (validateAllFields()){
            val newRide: Ride = createNewRide(app)
            val pressedGroup = vm.pressedGroup
            app.ridesPerGroup.addRide(newRide, pressedGroup.value?.name!!)
            vm.srcOrDest = ""


            sendNotification()

            Navigation.findNavController(aView).navigate(R.id.action_newRide2_to_dashboard)
        }
    }


    private fun getEditableET() : EditText{
        return if (toHuji){
            srcET
        }else{
            destET
        }
    }


    private fun getSrcOrDestStr() : String{
        return if (toHuji){
            "source"
        }else{
            "destination"
        }
    }





    private fun validateAllFields(): Boolean{
        val et = getEditableET()
        if (et.text.isEmpty()){
            Toast.makeText(activity, "you must fill ${getSrcOrDestStr()}",  Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun audioNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "New Ride Notification",
                "New Ride Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager: NotificationManager =
                activity?.getSystemService(NotificationManager::class.java)!!

            manager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        // todo: change to something that works
        val builder =
            activity?.let { it1 -> NotificationCompat.Builder(it1, "New Ride Notification") }
        builder?.setContentTitle("New Ride has been created")
        builder?.setContentText("click here to see more details")
        builder?.setSmallIcon(R.drawable.ic_baseline_notifications_24)
        builder?.setAutoCancel(true)

        val manageCompat = activity?.let { it1 -> NotificationManagerCompat.from(it1) }
        builder?.build()?.let { it1 -> manageCompat?.notify(1, it1) }
    }


    private fun findViews() {
        srcET = aView.findViewById(R.id.source_edit_text)
        destET = aView.findViewById(R.id.dest_edit_text)
        stops = aView.findViewById(R.id.autoCompleteStops)
        comments = aView.findViewById(R.id.autoCompleteComments)
        timerTextView = aView.findViewById(R.id.time_edit_btn)
        srcDestImg = aView.findViewById(R.id.srcDestImg)
        switchDirectionBtn = aView.findViewById(R.id.switchDirectionBtn)
    }

    private fun createNewRide(app: HujiRideApplication): Ride {
        return Ride(
            srcET.text.toString(),
            destET.text.toString(),
            "$timeHour : $timeMinutes",
            arrayListOf(stops.text.toString()),
            arrayListOf(comments.text.toString()),
            app.userDetails.userFirstName,
            app.userDetails.userLastName,
            app.userDetails.userPhoneNumber,
            UUID.randomUUID(),
            toHuji
        )
    }

    private fun timeDialog() {
        val timePickerDialog =
            TimePickerDialog(
                activity, { _, hourOfDay, minutes ->
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

    private fun designSwitchDirection(
        img: ImageView, constWay: EditText,
        notConstWay: EditText, resOfImg: Int
    ) {
        img.setImageResource(resOfImg)
        constWay.setText(getString(R.string.destHujiField))
        constWay.setTextColor(Color.BLACK)
        notConstWay.isEnabled = true
        constWay.isEnabled = false
        if (vm.srcOrDest != "") {
            notConstWay.setText(vm.srcOrDest)
        } else {

            notConstWay.text?.clear()
        }
    }


    private fun setDirection() {
        if (toHuji) {
            designSwitchDirection(srcDestImg, destET, srcET, R.drawable.resource_switch)

        } else {
            designSwitchDirection(srcDestImg, srcET, destET, R.drawable.switchfromhuji)
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

        } else {
            setDirection()
        }

    }

    private fun setDetails() {
        if (toHuji) {
            syncVmAndET(srcET)
        } else {
            syncVmAndET(destET)
        }
        toHuji = !toHuji
        vm.toHuji = toHuji
    }


    private fun syncVmAndET(editText: EditText) {
        if (editText.text?.isEmpty() != true) {
            vm.srcOrDest = editText.text.toString()
        } else {
            vm.srcOrDest = ""
        }
    }


}