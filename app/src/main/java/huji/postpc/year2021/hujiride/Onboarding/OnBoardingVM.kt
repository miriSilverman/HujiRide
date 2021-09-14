package huji.postpc.year2021.hujiride.Onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnBoardingVM: ViewModel()  {
    val progress: MutableLiveData<Int> by lazy { MutableLiveData<Int>(0) }

    var firstName: String? = null
    var lastName: String? = null

    var phoneNumber: String? = null
    var idNumber: String? = null


    var tasksArr = booleanArrayOf(true, false, false, false)
    var doneArr : MutableLiveData<BooleanArray> = MutableLiveData(tasksArr)
    var doneOnBoard: MutableLiveData<Boolean> = MutableLiveData(false)

    fun doneTask(taskNum: Int)
    {
        if (taskNum == (tasksArr.size))
        {
            doneOnBoard.value = true;
        }else
        {
            tasksArr[taskNum] = true;
            doneArr.value = tasksArr
        }
    }


    fun unDoneTask(taskNum: Int)
    {
        tasksArr[taskNum] = false;
        doneArr.value = tasksArr
    }



}