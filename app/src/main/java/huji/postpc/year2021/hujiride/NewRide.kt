package huji.postpc.year2021.hujiride

import android.app.*
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import huji.postpc.year2021.hujiride.Rides.RidesViewModel
import huji.postpc.year2021.hujiride.database.Ride

import java.util.*
import android.widget.RadioButton

import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable

import android.util.Log

import android.widget.EditText

import android.widget.RadioGroup
import huji.postpc.year2021.hujiride.database.Ride as ClientRide

import android.widget.CheckBox
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.core.GeoHash
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.Timestamp
import huji.postpc.year2021.hujiride.database.dateFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


/**
 * create new ride as driver
 */
class NewRide : Fragment() {

    private lateinit var aView: View
    private lateinit var timerTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var commentsTextView: TextView
    private var timeHour: Int = 0
    private var timeMinutes: Int = 0
    private var timeFormat = ""
    private lateinit var srcET: AutocompleteSupportFragment
    private lateinit var destET: AutocompleteSupportFragment
    private lateinit var destTextView: TextView
    private lateinit var srcTextView: TextView
    private var srcOrDestStr = ""
    private var latLng: LatLng = LatLng(0.0, 0.0)


    private lateinit var comments: ArrayList<String>
    private var otherComment = ""

    private var checkedComments : ArrayList<Boolean> = arrayListOf(false, false, false, false, false, false)
    private lateinit var srcDestImg: ImageView
    private lateinit var switchDirectionBtn: Button
    private var toHuji: Boolean = true
    private lateinit var vm: RidesViewModel
    private lateinit var app: HujiRideApplication
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private lateinit var dateListener :DatePickerDialog.OnDateSetListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun onResume() {
        super.onResume()
//        val stopsSortItems = resources.getStringArray(R.array.stops_list)
//        val stopsArrayAdapter = ArrayAdapter(requireContext(), R.layout.sort_item, stopsSortItems)
//        aView.findViewById<AutoCompleteTextView>(R.id.autoCompleteStops)
//          //  ?.setAdapter(stopsArrayAdapter)
//
//
//        val commentsSortItems = resources.getStringArray(R.array.comments_list)
//        val commentsArrayAdapter =
//            ArrayAdapter(requireContext(), R.layout.sort_item, commentsSortItems)
//        aView.findViewById<AutoCompleteTextView>(R.id.autoCompleteComments)
//            ?.setAdapter(commentsArrayAdapter)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        aView = inflater.inflate(R.layout.fragment_new_ride, container, false)
        vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
        app = HujiRideApplication.getInstance()
        srcOrDestStr = vm.srcOrDest
        latLng = vm.latLng
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        findViews()


        autoCompletePlaces(srcET)
        autoCompletePlaces(destET)

        setSrcOrDest()

        comments=ArrayList()
        switchDirectionBtn.setOnClickListener {
            setDetails()
            setDirection()
        }

        timerTextView.setOnClickListener {
            timeDialog()
        }

        dateListener = DatePickerDialog.OnDateSetListener(){
                datePicker: DatePicker, aYear: Int, aMonth: Int, dayOfMonth: Int ->
            val m = aMonth + 1
            val date = "$dayOfMonth/$m/$aYear"
            dateTextView.setText(date)
            day = dayOfMonth
            year = aYear
            month = aMonth

        }

        dateTextView.setOnClickListener {
            val picker = DatePickerDialog(
                requireActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                dateListener, year, month, day
            )

            picker.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            picker.show()
        }

        commentsTextView.setOnClickListener{
            commentsDialog()
        }

        audioNotification()


        aView.findViewById<ImageView>(R.id.done_btn)?.setOnClickListener {
            onPressedAddNewRide()
        }

        return aView
    }



    private fun autoCompletePlaces(autocompleteFragment: AutocompleteSupportFragment) {
        // places search bar
        Places.initialize(requireActivity(), "AIzaSyDTcekEAFGq-VG0MCPTNsYSwt9dKI8rIZA")
        val placesClient = Places.createClient(requireActivity())

        // Initialize the AutocompleteSupportFragment.
        //        val autocompleteFragment =
        //            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
        //                    as AutocompleteSupportFragment

//        autocompleteFragment =
//            childFragmentManager.findFragmentById(R.id.place_autocomplete_fragment_src)
//                    as AutocompleteSupportFragment
        // Specify the types of place data to return.
        autocompleteFragment
            .setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
            .setCountry("IL")
            .setHint("חפש מוצא...") // TODO translated version?

        val TAG = "SEARCH"
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: ${place.name}, ${place.id}, ${place.latLng}")
                srcOrDestStr = place.name.toString()
                if (place.latLng != null){
                    latLng = place.latLng!!
                }
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
    }



    private fun onPressedAddNewRide() {
        if (validateAllFields()){
            val newRide: Ride = createNewRide(app)
            val pressedGroup = vm.pressedGroup

            GlobalScope.launch (Dispatchers.IO) {
                app.db.addRide(newRide, app.userDetails.clientUniqueID,
                    pressedGroup.value!!.name)
                withContext(Dispatchers.Main) {
                    vm.srcOrDest = ""


                    sendNotification()

                    Navigation.findNavController(aView).navigate(R.id.action_newRide2_to_dashboard)

                }

            }




        }
    }


    private fun getEditableET() : AutocompleteSupportFragment{
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
        // todo: validate
//        if (et.text.isEmpty()){
//            Toast.makeText(activity, "you must fill ${getSrcOrDestStr()}",  Toast.LENGTH_SHORT).show()
//            return false
//        }
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
        srcET = childFragmentManager.findFragmentById(R.id.place_autocomplete_fragment_src)
                as AutocompleteSupportFragment
        destET = childFragmentManager.findFragmentById(R.id.place_autocomplete_fragment_dest)
                as AutocompleteSupportFragment

        commentsTextView = aView.findViewById(R.id.comments_edit_btn)
        timerTextView = aView.findViewById(R.id.time_edit_btn)
        srcDestImg = aView.findViewById(R.id.srcDestImg)
        dateTextView = aView.findViewById(R.id.date_edit_btn)
        switchDirectionBtn = aView.findViewById(R.id.switchDirectionBtn)
        srcTextView = aView.findViewById(R.id.src_huji)
        destTextView = aView.findViewById(R.id.dest_huji)
    }

    private fun createNewRide(app: HujiRideApplication): Ride {

        val d = Date()
        val c = Calendar.getInstance()
        c.time = d
        c.set(Calendar.HOUR_OF_DAY, timeHour)
        c.set(Calendar.MINUTE, timeMinutes)
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, day)
        val t = Timestamp(c.time)

        return Ride(
            time= timeFormat,
            timeStamp= t,
            stops = ArrayList(),
            comments = comments,
            driverID = app.userDetails.clientUniqueID,
            destName = srcOrDestStr,
            lat = latLng.latitude,
            long = latLng.longitude,
            geoHash = GeoFireUtils.getGeoHashForLocation(GeoLocation(latLng.latitude, latLng.longitude)),
            isDestinationHuji = toHuji
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

                    val timeFrm = SimpleDateFormat("HH:mm")
                    val format = timeFrm.format(calendar.time)
                    timeFormat = format
                    timerTextView.text = "Leaving at $format"
                }, 12, 0, false
            )

        timePickerDialog.updateTime(timeHour, timeMinutes)
        timePickerDialog.show()
    }

    private fun getTextFromBox(box: CheckBox)
    {
        if (box.isChecked()){
            comments.add(box.text.toString())
        }

    }




    private fun commentsDialog(){
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(activity)
        val boxes = ArrayList<android.widget.CheckBox>()

        val view = layoutInflater.inflate(R.layout.comments_alert_dialog, null)
        val smokingCheckBox : CheckBox = view.findViewById(R.id.smokingCheckBox)
        val vaccineCheckBox : CheckBox = view.findViewById(R.id.vaccineCheckBox)
        val womenCheckBox : CheckBox = view.findViewById(R.id.womenCheckBox)
        val distanceCheckBox : CheckBox = view.findViewById(R.id.distanceCheckBox)
        val StopsCheckBox : CheckBox = view.findViewById(R.id.StopsCheckBox)
        val onTimeCheckBox : CheckBox = view.findViewById(R.id.onTimeCheckBox)
        val otherText : EditText = view.findViewById(R.id.othersText)


        setCheckBoxes(
            smokingCheckBox,
            vaccineCheckBox,
            womenCheckBox,
            distanceCheckBox,
            StopsCheckBox,
            onTimeCheckBox,
            otherText
        )

        builder.setView(view)
        builder.setTitle("Comments")
        builder.setPositiveButton("Done",
            DialogInterface.OnClickListener { d, m ->
                comments.clear()
                getTextFromBox(smokingCheckBox)
                getTextFromBox(vaccineCheckBox)
                getTextFromBox(womenCheckBox)
                getTextFromBox(distanceCheckBox)
                getTextFromBox(StopsCheckBox)
                getTextFromBox(onTimeCheckBox)
                if (!otherText.text.isEmpty()){
                    comments.add(otherText.text.toString())
                }


                showComments()

                setCheckedArr(
                    smokingCheckBox,
                    vaccineCheckBox,
                    womenCheckBox,
                    distanceCheckBox,
                    StopsCheckBox,
                    onTimeCheckBox,
                    otherText
                )


            })
        dialog = builder.create()
        dialog.show()


    }

    private fun showComments() {
        var commentStr = ""
        for (c in comments) {
            commentStr += c
            commentStr += "\n"
        }
        commentsTextView.setText(commentStr)
    }

    private fun setCheckedArr(
        smokingCheckBox: CheckBox,
        vaccineCheckBox: CheckBox,
        womenCheckBox: CheckBox,
        distanceCheckBox: CheckBox,
        StopsCheckBox: CheckBox,
        onTimeCheckBox: CheckBox,
        otherText: EditText
    ) {
        checkedComments[0] = smokingCheckBox.isChecked
        checkedComments[1] = vaccineCheckBox.isChecked
        checkedComments[2] = womenCheckBox.isChecked
        checkedComments[3] = distanceCheckBox.isChecked
        checkedComments[4] = StopsCheckBox.isChecked
        checkedComments[5] = onTimeCheckBox.isChecked
        otherComment = otherText.text.toString()
    }

    private fun setCheckBoxes(
        smokingCheckBox: CheckBox,
        vaccineCheckBox: CheckBox,
        womenCheckBox: CheckBox,
        distanceCheckBox: CheckBox,
        StopsCheckBox: CheckBox,
        onTimeCheckBox: CheckBox,
        otherText: EditText
    ) {
        smokingCheckBox.isChecked = checkedComments[0]
        vaccineCheckBox.isChecked = checkedComments[1]
        womenCheckBox.isChecked = checkedComments[2]
        distanceCheckBox.isChecked = checkedComments[3]
        StopsCheckBox.isChecked = checkedComments[4]
        onTimeCheckBox.isChecked = checkedComments[5]
        otherText.setText(otherComment)
    }


    private fun designSwitchDirection(img:ImageView, constWay: AutocompleteSupportFragment,
                                      notConstWay: AutocompleteSupportFragment, resOfImg: Int,
                                      tvVisible: TextView, tvInvisible: TextView) {
        img.setImageResource(resOfImg)
        notConstWay.view?.visibility = View.VISIBLE
        constWay.view?.visibility = View.INVISIBLE
        tvVisible.visibility = View.VISIBLE
        tvInvisible.visibility = View.INVISIBLE

        if (srcOrDestStr != "") {
            notConstWay.setText(vm.srcOrDest)
        }
    }
//    private fun designSwitchDirection(
//        img: ImageView, constWay: EditText,
//        notConstWay: EditText, resOfImg: Int
//    ) {
//        img.setImageResource(resOfImg)
//        constWay.setText(getString(R.string.destHujiField))
//        constWay.setTextColor(Color.BLACK)
//        notConstWay.isEnabled = true
//        constWay.isEnabled = false
//        if (vm.srcOrDest != "") {
//            notConstWay.setText(vm.srcOrDest)
//        } else {
//
//            notConstWay.text?.clear()
//        }
//    }

    private fun setDirection() {
        if (toHuji) {
            designSwitchDirection(srcDestImg, destET, srcET, R.drawable.resource_switch, destTextView, srcTextView)
        } else {
            designSwitchDirection(srcDestImg, srcET, destET, R.drawable.switchfromhuji, srcTextView, destTextView)
        }
        vm.toHuji = toHuji
    }


//    private fun setDirection() {
//        if (toHuji) {
//            designSwitchDirection(srcDestImg, destET, srcET, R.drawable.resource_switch)
//
//        } else {
//            designSwitchDirection(srcDestImg, srcET, destET, R.drawable.switchfromhuji)
//        }
//    }


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
            syncVmAndET()
        } else {
            syncVmAndET()
        }
        toHuji = !toHuji
        vm.toHuji = toHuji
    }


//    private fun syncVmAndET(editText: EditText) {
//        if (editText.text?.isEmpty() != true) {
//            vm.srcOrDest = editText.text.toString()
//        } else {
//            vm.srcOrDest = ""
//        }
//    }

    private fun syncVmAndET() {
        if (srcOrDestStr.isNotEmpty()) {
            vm.srcOrDest = srcOrDestStr
        } else {
            vm.srcOrDest = ""
        }
    }


}