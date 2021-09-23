package huji.postpc.year2021.hujiride.Rides

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import huji.postpc.year2021.hujiride.R


/**
 * ride's details
 */
class RidesDetails : Fragment() {

    private lateinit var aView: View

    private lateinit var backToRidesBtn: Button
    private lateinit var backToGroupsBtn: Button
    private lateinit var contactDriverBtn: Button

    private lateinit var srcTV: TextView
    private lateinit var destTV: TextView
    private lateinit var timeTV: TextView
    private lateinit var commentsTV: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    fun findViews() {
        backToRidesBtn = aView.findViewById<Button>(R.id.back_to_closest_rides)
        backToGroupsBtn = aView.findViewById<Button>(R.id.back_to_groups)
        contactDriverBtn = aView.findViewById<Button>(R.id.contact_driver_btn)

        srcTV = aView.findViewById<TextView>(R.id.source)
        destTV = aView.findViewById<TextView>(R.id.destination)
        timeTV = aView.findViewById<TextView>(R.id.time)
        commentsTV = aView.findViewById<TextView>(R.id.comments)
    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        aView =  inflater.inflate(R.layout.fragment_rides_details, container, false)
        findViews()

        val vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)



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
            timeTV.text = ride.time


            var comments = ""
            for (s in ride.comments)
            {
                comments += "\n"+s
            }
            commentsTV.text = comments

        }

        return aView
    }


}