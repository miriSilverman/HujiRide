package huji.postpc.year2021.hujiride.Groups

import kotlin.collections.ArrayList

class GroupsData {

    var mutableDataFilteredGroups: ArrayList<Pair<String,String>> = arrayListOf()


    fun setFiltered(map: Map<String,String>){
        val filtered : ArrayList<Pair<String,String>> = arrayListOf()
        for (p in map){
            filtered.add(Pair(p.key, p.value))
        }
        mutableDataFilteredGroups = filtered
    }

    fun setFiltered(array: ArrayList<Pair<String,String>>){
        mutableDataFilteredGroups = array
    }





}