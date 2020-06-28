package com.easycoder.simpletask.data.dataSources

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.easycoder.simpletask.data.main.model.TaskModel

/**
 * Created by HM SHOHRAB on 27,June,2020
 * easyCoder company,
 * Dhaka, Bangladesh.
 * hmshohrabpc@gmail.com
 * Let's start coding :)
 * Bismillah Hir Rahman Nir Raheem
 */
@Dao
interface TaskModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertdata(taskModel: TaskModel): Long

    @Query("SELECT * FROM Task_table")
    fun getAlldata(): LiveData<MutableList<TaskModel>>

    @Query("SELECT * FROM Task_table")
    fun getAlldata1(): MutableList<TaskModel>
}