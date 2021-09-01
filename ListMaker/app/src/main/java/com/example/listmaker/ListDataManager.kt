package com.example.listmaker

import android.content.Context
import androidx.preference.PreferenceManager

class ListDataManager(private val context: Context) {

    fun saveList(list: TaskList?){
        val sharedPrefs=PreferenceManager.getDefaultSharedPreferences(context).edit()
        if (list != null) {
            sharedPrefs.putStringSet(list.name , list.tasks.toHashSet())
        }
        sharedPrefs.apply()
    }
    fun readList(): ArrayList<TaskList>{
        val sharedPrefs=PreferenceManager.getDefaultSharedPreferences(context)
        val contenrs =sharedPrefs.all
        val tasklist = ArrayList<TaskList>()

        for(TaskList in contenrs) {
            val taskItems =ArrayList(TaskList.value as HashSet<String>)
            val list =TaskList(TaskList.key , taskItems)
            tasklist.add(list)
        }
        return tasklist

    }
}