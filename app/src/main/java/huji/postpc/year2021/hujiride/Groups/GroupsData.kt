package huji.postpc.year2021.hujiride.Groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import huji.postpc.year2021.hujiride.SearchGroupItem
import java.util.*
import kotlin.collections.ArrayList

class GroupsData {

    val neighborhoods: List<SearchGroupItem> = java.util.ArrayList(Arrays.asList(SearchGroupItem("Malcha", false),
            SearchGroupItem("Bakaa", false), SearchGroupItem("Talpiyot", false),
            SearchGroupItem("Pisga zeev", false), SearchGroupItem("Gilo", false)))


    // groups that were added
    private val mutableDataGroups: MutableLiveData<MutableList<SearchGroupItem>> = MutableLiveData<MutableList<SearchGroupItem>>()
    var liveDataGroups: LiveData<MutableList<SearchGroupItem>> = mutableDataGroups

    // all groups as <name of group, checked or not>
//    val mutableDataCheckedGroups: MutableLiveData<MutableMap<String, SearchGroupItem>> = MutableLiveData<MutableMap<String, SearchGroupItem>>()
//    var liveDataCheckedGroups: LiveData<MutableMap<String, SearchGroupItem>> = mutableDataCheckedGroups

    val mutableDataCheckedGroups: MutableLiveData<MutableList<SearchGroupItem>> = MutableLiveData<MutableList<SearchGroupItem>>()
//    var liveDataCheckedGroups: LiveData<MutableList<SearchGroupItem>> = mutableDataCheckedGroups


    private var groupsList : ArrayList<SearchGroupItem> = arrayListOf()


    fun setChecked(arrayList: ArrayList<SearchGroupItem>){
        mutableDataCheckedGroups.value = arrayList
    }



    fun getGroups() : List<SearchGroupItem> {
        var newList = ArrayList<SearchGroupItem>()
        liveDataGroups.value?.let { newList.addAll(it) }
        return newList
    }


    fun addGroup(newGroup: SearchGroupItem) {
        groupsList.add(newGroup)
        mutableDataGroups.value = groupsList
    }


    fun removeGroup(group: SearchGroupItem){
        groupsList.remove(group)
        mutableDataGroups.value = groupsList
    }

//    fun isGroupChecked(nameOfGroup: String): Boolean? {
//        return mutableDataCheckedGroups.value?.get(nameOfGroup)?.checked
//    }









}