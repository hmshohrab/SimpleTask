package com.easycoder.simpletask.data.main

import android.content.Context
import com.easycoder.simpletask.core.data.localDB.AppDatabase
import com.easycoder.simpletask.core.interfaces.RequestCompleteListener
import com.easycoder.simpletask.data.main.model.TaskModel


/**
 * Created by HM SHOHRAB on 27,June,2020
 * easyCoder company,
 * Dhaka, Bangladesh.
 * hmshohrabpc@gmail.com
 * Let's start coding :)
 * Bismillah Hir Rahman Nir Raheem
 */

class TaskRepositoryImpl(private val context: Context, val db: AppDatabase) : TaskRepository {

    override fun getTaskList(): MutableList<TaskModel> {

        return db.taskModelDao().getAlldata1()

    }

    override fun setTaskList(callbacks: RequestCompleteListener<MutableList<TaskModel>>) {
        val taskModelList: MutableList<TaskModel> = mutableListOf()
        taskModelList.add(
            TaskModel(
                "To Do",
                0
            )
        )
        taskModelList.add(
            TaskModel(
                "Notes",
                0
            )
        )
        taskModelList.add(
            TaskModel(
                "Work",
                0
            )
        )

        if (taskModelList.isNotEmpty()) {
            var pos = 0
            for (item in taskModelList) {
                db.taskModelDao().insertdata(item)
                pos++
            }
            if (pos == taskModelList.size) {
                callbacks.onRequestSuccess(taskModelList)
            }
        } else {
            callbacks.onRequestFailed("Task is Empty.")
        }

    }
}