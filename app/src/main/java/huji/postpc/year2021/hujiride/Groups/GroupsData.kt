package huji.postpc.year2021.hujiride.Groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GroupsData {

    private val mutableDataGroups: MutableLiveData<MutableList<Group>> = MutableLiveData<MutableList<Group>>()
    var liveDataGroups: LiveData<MutableList<Group>> = mutableDataGroups

    private var groupsList : ArrayList<Group> = arrayListOf()


    fun getGroups() : List<Group> {
        var newList = ArrayList<Group>()
//        newList.addAll(groupsList)
        liveDataGroups.value?.let { newList.addAll(it) }
        return newList
    }


    fun addGroup(newGroup: Group) {
        groupsList.add(newGroup)
        mutableDataGroups.value = groupsList
    }

    fun removeGroup(group: Group){
        groupsList.remove(group)
        mutableDataGroups.value = groupsList
    }









}