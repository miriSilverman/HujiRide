package huji.postpc.year2021.hujiride

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnBoardingVM: ViewModel()  {

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