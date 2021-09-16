package huji.postpc.year2021.hujiride.Groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem
import huji.postpc.year2021.hujiride.database.JERUSALEM_NEIGHBORHOODS
import kotlin.collections.ArrayList

class GroupsData {


    val neighborhoods = JERUSALEM_NEIGHBORHOODS



    // groups that were added
    private val mutableDataGroups: MutableLiveData<MutableList<String>> = MutableLiveData<MutableList<String>>()
    var liveDataGroups: LiveData<MutableList<String>> = mutableDataGroups



    // the filtered list
    val mutableDataFilteredGroups: MutableLiveData<MutableList<String>> = MutableLiveData<MutableList<String>>()


    private var groupsList : ArrayList<String> = arrayListOf()





    fun setFiltered(arrayList: ArrayList<String>){
        mutableDataFilteredGroups.value = arrayList
    }


    fun getFiltered() : MutableList<String>? {
        return mutableDataFilteredGroups.value
    }




    fun getGroups() : List<String> {
        val newList = ArrayList<String>()
        liveDataGroups.value?.let { newList.addAll(it) }
        return newList
    }


    fun addGroup(newGroup: String) {
        groupsList.add(newGroup)
        mutableDataGroups.value = groupsList
    }


    fun removeGroup(group: String){
        groupsList.remove(group)
        mutableDataGroups.value = groupsList
    }






}