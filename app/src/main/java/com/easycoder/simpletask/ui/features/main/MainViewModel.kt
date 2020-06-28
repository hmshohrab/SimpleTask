package com.easycoder.simpletask.ui.features.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.easycoder.simpletask.core.data.localDB.AppDatabase
import com.easycoder.simpletask.core.data.preference.AppPreference
import com.easycoder.simpletask.core.ui.BaseViewModel
import com.easycoder.simpletask.data.main.TaskRepositoryImpl
import com.easycoder.simpletask.data.main.model.TaskModel

/**
 * Created by HM SHOHRAB on 26,June,2020
 * easyCoder company,
 * Dhaka, Bangladesh.
 * hmshohrabpc@gmail.com
 * Let's start coding :)
 * Bismillah Hir Rahman Nir Raheem
 */
class MainViewModel(application: Application) : BaseViewModel(application) {

    var taskRepository: TaskRepositoryImpl? = null
    var appDatabase: AppDatabase? = null
    var appPreference: AppPreference? = null


    init {
        appDatabase = AppDatabase.getInstance(application)!!
        taskRepository = TaskRepositoryImpl(application, appDatabase!!)

    }

    val massage = MutableLiveData<String>()
    val taskListFromDb: MutableLiveData<List<TaskModel>> = MutableLiveData() //later

    fun getTaskList(): LiveData<MutableList<TaskModel>> {

        return roomDbInstance().taskModelDao().getAlldata()

    }

    fun insertData(taskModel: TaskModel): Long {

        return roomDbInstance().taskModelDao().insertdata(taskModel)

    }

    fun roomDbInstance(): AppDatabase {
        return appDatabase!!
    }
}