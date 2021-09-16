package huji.postpc.year2021.hujiride.Groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem
import huji.postpc.year2021.hujiride.database.JERUSALEM_NEIGHBORHOODS
import kotlin.collections.ArrayList

class GroupsData {

//    val neighborhoods: List<SearchGroupItem> = java.util.ArrayList(
//        listOf(SearchGroupItem("Malcha", false),
//            SearchGroupItem("Bakaa", false), SearchGroupItem("Talpiyot", false),
//            SearchGroupItem("Pisgat zeev", false), SearchGroupItem("Gilo", false))
//    )

    val neighborhoods = JERUSALEM_NEIGHBORHOODS

    var neighborhoodsChecked = ArrayList<Boolean>(neighborhoods.size)
//    var neighborhoodsChecked = ArrayList<Boolean>(neighborhoods.size).fill(false)


    // groups that were added
    private val mutableDataGroups: MutableLiveData<MutableList<SearchGroupItem>> = MutableLiveData<MutableList<SearchGroupItem>>()
    var liveDataGroups: LiveData<MutableList<SearchGroupItem>> = mutableDataGroups



    // the filtered list
//    val mutableDataFilteredGroups: MutableLiveData<MutableList<SearchGroupItem>> = MutableLiveData<MutableList<SearchGroupItem>>()
    val mutableDataFilteredGroups: MutableLiveData<MutableList<String>> = MutableLiveData<MutableList<String>>()


    private var groupsList : ArrayList<SearchGroupItem> = arrayListOf()

    // the groups that were chosen in one use of search group
    var currentChanges : ArrayList<SearchGroupItem> = arrayListOf()


//    fun setFiltered(arrayList: ArrayList<SearchGroupItem>){
//        mutableDataFilteredGroups.value = arrayList
//    }


    fun setFiltered(arrayList: ArrayList<String>){
        mutableDataFilteredGroups.value = arrayList
    }


    fun getFiltered() : MutableList<String>? {
        return mutableDataFilteredGroups.value
    }

    fun initNeighborhoodsChecked(){
        neighborhoodsChecked.fill(false)
    }



    fun getGroups() : List<SearchGroupItem> {
        val newList = ArrayList<SearchGroupItem>()
        liveDataGroups.value?.let { newList.addAll(it) }
        return newList
    }


    fun addGroup(newGroup: SearchGroupItem) {
        groupsList.add(newGroup)
        mutableDataGroups.value = groupsList
        val idx = neighborhoods.indexOf(newGroup.name)
        neighborhoodsChecked[idx] = true
    }


    fun removeGroup(group: SearchGroupItem){
//        if (groupsList.contains(group)){
//            groupsList.remove(group)
//            mutableDataGroups.value = groupsList
//        }

        groupsList.remove(group)
        mutableDataGroups.value = groupsList
        val idx = neighborhoods.indexOf(group.name)
        neighborhoodsChecked[idx] = false
    }






}