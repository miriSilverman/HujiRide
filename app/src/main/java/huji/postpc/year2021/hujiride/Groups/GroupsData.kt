package huji.postpc.year2021.hujiride.Groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem
import kotlin.collections.ArrayList

class GroupsData {

    val neighborhoods: List<SearchGroupItem> = java.util.ArrayList(
        listOf(SearchGroupItem("Malcha", false),
            SearchGroupItem("Bakaa", false), SearchGroupItem("Talpiyot", false),
            SearchGroupItem("Pisgat zeev", false), SearchGroupItem("Gilo", false))
    )


    // groups that were added
    private val mutableDataGroups: MutableLiveData<MutableList<SearchGroupItem>> = MutableLiveData<MutableList<SearchGroupItem>>()
    var liveDataGroups: LiveData<MutableList<SearchGroupItem>> = mutableDataGroups



    // the filtered list
    val mutableDataCheckedGroups: MutableLiveData<MutableList<SearchGroupItem>> = MutableLiveData<MutableList<SearchGroupItem>>()


    private var groupsList : ArrayList<SearchGroupItem> = arrayListOf()
    var currentChanges : ArrayList<SearchGroupItem> = arrayListOf()


    fun setChecked(arrayList: ArrayList<SearchGroupItem>){
        mutableDataCheckedGroups.value = arrayList
    }



    fun getGroups() : List<SearchGroupItem> {
        val newList = ArrayList<SearchGroupItem>()
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



//    ######################  DIALOG POPUP ##########################

    var checkedItems: ArrayList<Boolean> = arrayListOf()
    var userItems: ArrayList<Int> = arrayListOf()

    fun initVars(){
        for (item: SearchGroupItem in neighborhoods){
            checkedItems.add(item.checked)
        }
    }










}