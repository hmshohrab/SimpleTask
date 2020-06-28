package com.easycoder.simpletask.data.main

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

interface TaskRepository {

    fun getTaskList(): MutableList<TaskModel>
    fun setTaskList(callbacks: RequestCompleteListener<MutableList<TaskModel>>)
}