package huji.postpc.year2021.hujiride.Groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import huji.postpc.year2021.hujiride.SearchGroups.SearchGroupItem
import java.security.KeyStore
import kotlin.collections.ArrayList

class GroupsData {





    // groups that were added
    private val mutableDataGroups: MutableLiveData<MutableList<String>> = MutableLiveData<MutableList<String>>()
    var liveDataGroups: LiveData<MutableList<String>> = mutableDataGroups



    // the filtered list
//    val mutableDataFilteredGroups: MutableLiveData<MutableList<String>> = MutableLiveData<MutableList<String>>()
//    var mutableDataFilteredGroups: Map<String,String> = HashMap()
    var mutableDataFilteredGroups: ArrayList<Pair<String,String>> = arrayListOf()


    private var groupsList : ArrayList<String> = arrayListOf()





    fun setFiltered(map: HashMap<String,String>){
        val filtered : ArrayList<Pair<String,String>> = arrayListOf()
        for (p in map){
            filtered.add(Pair(p.key, p.value))
        }
        mutableDataFilteredGroups = filtered
    }

    fun setFiltered(array: ArrayList<Pair<String,String>>){
        mutableDataFilteredGroups = array
    }



//    fun getFiltered() : MutableList<String>? {
//        return mutableDataFilteredGroups.value
//    }




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