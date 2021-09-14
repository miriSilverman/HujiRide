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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import huji.postpc.year2021.hujiride.Rides.RidesViewModel
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem


/**
 * create or find new ride
 */
class SearchHome : Fragment() {

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

        findViews(view)

        toHuji = vm.toHuji
        setDirection()

        switchDirectionBtn.setOnClickListener {
            setDetails()
            toHuji = !toHuji
            vm.toHuji = toHuji
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

    private fun findViews(view: View) {
        srcET = view.findViewById(R.id.source_edit_text)
        destET = view.findViewById(R.id.dest_edit_text)
        srcDestImg = view.findViewById(R.id.srcDestImg)
        switchDirectionBtn = view.findViewById(R.id.switchDirectionBtn)
    }


    private fun setDetails(){
        if (toHuji){
            syncVmAndET(srcET)
        }else{
            syncVmAndET(destET)

        }
    }

    private fun syncVmAndET(editText: EditText) {
        if (editText.text?.isEmpty() != true) {
            vm.srcOrDest = editText.text.toString()
        } else {
            vm.srcOrDest = ""
        }
    }



    private fun setDirection() {
        if (toHuji) {
            designSwitchDirection(srcDestImg, destET, srcET, R.drawable.resource_switch)

        } else {
            designSwitchDirection(srcDestImg, srcET, destET, R.drawable.switchfromhuji)
        }
        vm.toHuji = toHuji
    }



    private fun designSwitchDirection(img:ImageView, constWay: EditText,
                                      notConstWay: EditText, resOfImg: Int) {
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




}