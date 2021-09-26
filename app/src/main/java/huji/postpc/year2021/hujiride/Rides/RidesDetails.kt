package huji.postpc.year2021.hujiride.Rides

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import huji.postpc.year2021.hujiride.R
import java.text.SimpleDateFormat


/**
 * ride's details
 */
class RidesDetails : Fragment() {

    private lateinit var aView: View

    private lateinit var backToRidesBtn: Button
    private lateinit var backToGroupsBtn: Button
    private lateinit var contactDriverBtn: Button
    private lateinit var shareRideBtn: Button

    private lateinit var srcTV: TextView
    private lateinit var destTV: TextView
    private lateinit var timeTV: TextView
    private lateinit var commentsTV: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    private fun findViews() {
        backToRidesBtn = aView.findViewById(R.id.back_to_closest_rides)
        backToGroupsBtn = aView.findViewById(R.id.back_to_groups)
        contactDriverBtn = aView.findViewById(R.id.contact_driver_btn)
        shareRideBtn = aView.findViewById(R.id.share_ride_btn)

        srcTV = aView.findViewById(R.id.source)
        destTV = aView.findViewById(R.id.destination)
        timeTV = aView.findViewById(R.id.time)
        commentsTV = aView.findViewById(R.id.comments)
    }





    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        aView =  inflater.inflate(R.layout.fragment_rides_details, container, false)
        findViews()

        val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)

        if (vm.fromDashboard){
            contactDriverBtn.text = "Delete ride"
        }else{
            contactDriverBtn.text = "contact driver"

        }


        backToRidesBtn.setOnClickListener {
            Navigation.findNavController(aView).navigate(R.id.action_ridesDetails_to_ridesList)
        }

        contactDriverBtn.setOnClickListener {
            Navigation.findNavController(aView).navigate(R.id.action_ridesDetails_to_driversDetails)
        }


        backToGroupsBtn.setOnClickListener {
            Navigation.findNavController(aView).navigate(R.id.action_ridesDetails_to_groups_home)
        }





        val ride = vm.pressedRide.value
        if (ride != null){

            var src = "HUJI"
            var dest = "HUJI"

            if (ride.isDestinationHuji){
                src = ride.destName
            }else{
                dest = ride.destName
            }

            srcTV.text = src
            destTV.text = dest
            val stamp = ride.timeStamp
            val dt = stamp.toDate()
            val datFrm = SimpleDateFormat("dd/MM/yyyy ")
            val timeFrm = SimpleDateFormat("HH:mm")
            timeTV.text = "${timeFrm.format(dt)}  at  ${datFrm.format(dt)}"





            var comments = ""
            for (s in ride.comments)
            {
                comments += "\n"+s
            }
            if (comments != ""){
                commentsTV.text = comments
            }


            shareRideBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                val body = "Sharing a ride with you"
                var commentMsg = ""
                if (comments != ""){
                    commentMsg = "Please be aware of the following things:$comments"
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