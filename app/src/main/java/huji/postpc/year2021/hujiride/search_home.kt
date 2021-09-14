package huji.postpc.year2021.hujiride

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import huji.postpc.year2021.hujiride.Rides.RidesViewModel
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem


/**
 * create or find new ride
 */
class search_home : Fragment() {

    private lateinit var srcDestImg: ImageView
    private lateinit var switchDirectionBtn: Button
    private var toHuji: Boolean = true

    private lateinit var srcET: EditText
    private lateinit var destET: EditText
    private lateinit var vm: RidesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_search_home, container, false)
        vm = ViewModelProvider(requireActivity()).get(RidesViewModel::class.java)
        vm.pressedGroup.value = SearchGroupItem("all", false)

        srcET = view.findViewById(R.id.source_edit_text)
        destET = view.findViewById(R.id.dest_edit_text)

        srcDestImg = view.findViewById(R.id.srcDestImg)
        switchDirectionBtn = view.findViewById(R.id.switchDirectionBtn)
        toHuji = vm.toHuji
        setDirection()

        switchDirectionBtn.setOnClickListener {
            toHuji = !toHuji
            setDirection()
        }

        view.findViewById<TextView>(R.id.driver_btn).setOnClickListener {
            setDetails()
            Navigation.findNavController(view).navigate(R.id.action_search_home_to_newRide2)
        }

        view.findViewById<TextView>(R.id.trampist_btn).setOnClickListener {
            setDetails()
            Navigation.findNavController(view).navigate(R.id.action_search_home_to_ridesList)
        }
        return view
    }




    private fun setDetails(){
        vm.toHuji = toHuji
        if (toHuji){
            if (srcET.text?.isEmpty() != true){
                vm.srcOrDest = srcET.text.toString()
            }
        }else{
            if (destET.text?.isEmpty() != true){
                vm.srcOrDest = destET.text.toString()
            }

        }

    }



    private fun setDirection() {

        if (toHuji) {

            srcDestImg.setImageResource(R.drawable.resource_switch)
            destET.setText(getString(R.string.destHujiField))
            destET.setTextColor(Color.BLACK)
            destET.isEnabled = false
            srcET.isEnabled = true
            srcET.text?.clear()

        } else {
            srcDestImg.setImageResource(R.drawable.switchfromhuji)
            srcET.setText(getString(R.string.destHujiField))
            srcET.setTextColor(Color.BLACK)
            destET.isEnabled = true
            srcET.isEnabled = false
            destET.text?.clear()


        }
        vm.toHuji = toHuji
    }







}