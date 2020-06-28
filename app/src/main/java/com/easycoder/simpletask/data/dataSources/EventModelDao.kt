package com.easycoder.simpletask.data.dataSources

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.easycoder.simpletask.data.completed_event.CompletedEvent
import com.easycoder.simpletask.data.event.EventModel


/**
 * Created by HM SHOHRAB on 27,June,2020
 * easyCoder company,
 * Dhaka, Bangladesh.
 * hmshohrabpc@gmail.com
 * Let's start coding :)
 * Bismillah Hir Rahman Nir Raheem
 */
@Dao
interface EventModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertdata(eventModel: EventModel): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCompleteEventData(eventModel: CompletedEvent): Long


    @Query("SELECT * FROM event_table")
    fun getAllData(): LiveData<MutableList<EventModel>>


    @Query("SELECT * FROM   event_table WHERE catId = :catId")
    fun getDataByCategoryId(catId: Int): LiveData<MutableList<EventModel>>

    @Query("SELECT * FROM   completed_event WHERE catId = :catId")
    fun getComEventDataByCategoryId(catId: Int): LiveData<MutableList<CompletedEvent>>


    @Query("UPDATE task_table SET tItems = tItems+1 WHERE id = :catId ")
    fun updateTaskData(catId: Int): Int

    @Query("UPDATE task_table SET tItems = tItems-1 WHERE id = :catId ")
    fun updateTaskData1(catId: Int): Int

    @Query("UPDATE event_table SET title = :title ,endDate =:endDate , note =:notes   WHERE id= :id ")
    fun updateEventData(title: String, endDate: Long, notes: String, id: String): Int

    @Query("DELETE FROM event_table WHERE id = :id ")
    fun deleteEventData(id: String): Int

    @Query("DELETE FROM completed_event WHERE id = :id ")
    fun deleteComEventData(id: String): Int
}