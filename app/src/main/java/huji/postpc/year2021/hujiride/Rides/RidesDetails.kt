package huji.postpc.year2021.hujiride.Rides

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import huji.postpc.year2021.hujiride.HujiRideApplication
import huji.postpc.year2021.hujiride.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat


/**
 * ride's details
 */
class RidesDetails : Fragment() {

    private lateinit var aView: View

    private lateinit var contactDriverBtn: Button
    private lateinit var shareRideBtn: Button

    private lateinit var srcTV: TextView
    private lateinit var destTV: TextView
    private lateinit var timeTV: TextView
    private lateinit var app: HujiRideApplication
    private lateinit var vm: RidesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    private fun findViews() {
        contactDriverBtn = aView.findViewById(R.id.contact_driver_btn)
        shareRideBtn = aView.findViewById(R.id.share_ride_btn)

        srcTV = aView.findViewById(R.id.source)
        destTV = aView.findViewById(R.id.destination)
        timeTV = aView.findViewById(R.id.time)
    }

    private fun deleteRide() {
        val ride = vm.pressedRide.value
        GlobalScope.launch(Dispatchers.IO) {
            app.db.deleteRide(ride!!.id)

            withContext(Dispatchers.Main) {
                Toast.makeText(activity, "Ride was deleted successfully", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(aView).navigate(R.id.action_ridesDetails_to_dashboard)
            }
        }
    }

    private fun deleteRideAlert(){


        AlertDialog.Builder(activity)
            .setTitle(R.string.deleteRidePublishedDialogTitle)
            .setMessage(R.string.deleteRidePublishedTxt)
            .setIcon(R.drawable.ic_delete)
            .setCancelable(false)
            .setNegativeButton("no", null)
            .setPositiveButton("yes") { _: DialogInterface, _: Int ->
                deleteRide()


            }
            .create().show()
    }

    private fun contactDriver() {
        Navigation.findNavController(aView).navigate(R.id.action_ridesDetails_to_driversDetails)
    }


    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        aView = inflater.inflate(R.layout.fragment_rides_details, container, false)
        findViews()
        app = HujiRideApplication.getInstance()
        vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)

        if (vm.fromDashboard) {
            contactDriverBtn.text = "Delete ride"
        } else {
            contactDriverBtn.text = "contact driver"

        }


        contactDriverBtn.setOnClickListener {
            if (vm.fromDashboard) {
                deleteRideAlert()
            } else {
                contactDriver()
            }
        }



        val ride = vm.pressedRide.value
        if (ride != null) {

            var src = "HUJI"
            var dest = "HUJI"

            if (ride.isDestinationHuji) {
                src = ride.destName
            } else {
                dest = ride.destName
            }

            srcTV.text = getString(R.string.ride_details_source, src)
            destTV.text = getString(R.string.ride_details_dest, dest)
            val stamp = ride.timeStamp
            val dt = stamp.toDate()
            val datFrm = SimpleDateFormat("dd/MM/yyyy ")
            val timeFrm = SimpleDateFormat("HH:mm")
//            timeTV.text = "${timeFrm.format(dt)}  at  ${datFrm.format(dt)}"
            timeTV.text = getString(R.string.ride_details_time, dt)

            val comments = ride.comments.filterNot { s -> s == "" }

            val commentsListView = aView.findViewById<ListView>(R.id.comments_listview)
            val commentsAdapter = ArrayAdapter<String>(requireContext(), R.layout.comment_layout_of_listview, comments)
            commentsListView.adapter = commentsAdapter


            shareRideBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                val body = "Sharing a ride with you"
                var commentMsg = ""
                if (comments.isNotEmpty()) {
                    val commentsBullets = comments.reduce { acc, s -> "$acc\n• $s" }
                    commentMsg = "Please be aware of the following things:\n$commentsBullets"

                }
                val sub = "Hi!\nYou might be interested in this ride:\n\n" +
                        "Its going to be at ${timeFrm.format(dt)}  at  ${datFrm.format(dt)}\n\n" +
                        "From $src to ${dest}\n\n" +
                        "${commentMsg}\n\n" +
                        "You are welcome to download the HujiRides in the following link\n" +
                        " http://play.google.com" //todo: change to real link
                intent.putExtra(Intent.EXTRA_TEXT, body)
                intent.putExtra(Intent.EXTRA_TEXT, sub)
                startActivity(Intent.createChooser(intent, "Share using"))
            }

        }


        return aView
    }


}