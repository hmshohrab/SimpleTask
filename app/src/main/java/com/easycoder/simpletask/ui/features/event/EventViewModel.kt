package com.easycoder.simpletask.ui.features.event

import android.app.Application
import androidx.lifecycle.LiveData
import com.easycoder.simpletask.core.data.localDB.AppDatabase
import com.easycoder.simpletask.core.ui.BaseViewModel
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
class EventViewModel(application: Application) : BaseViewModel(application) {
    private var appDatabase: AppDatabase? = null


    init {
        appDatabase = AppDatabase.getInstance(application)!!


    }

    fun insertData(e: EventModel): Long {

        return roomDbInstance().eventModelDao().insertdata(e)

    }

    fun insertCompleteEventData(e: CompletedEvent): Long {

        return roomDbInstance().eventModelDao().insertCompleteEventData(e)

    }

    fun updateCatData(catId: Int): Int {

        return roomDbInstance().eventModelDao().updateTaskData(catId)

    }

    fun updateCatData1(catId: Int): Int {

        return roomDbInstance().eventModelDao().updateTaskData1(catId)

    }

    fun updateEventData(eventModel: EventModel): Int {

        return appDatabase!!.eventModelDao()
            .updateEventData(eventModel.title, eventModel.endDate, eventModel.note, eventModel.id)

    }

    fun deleteEventData(eventModel: EventModel): Int {

        return roomDbInstance().eventModelDao()
            .deleteEventData(eventModel.id)

    }

    fun deleteComEventData(eventModel: CompletedEvent): Int {

        return roomDbInstance().eventModelDao()
            .deleteComEventData(eventModel.id)

    }

    fun getEventList(catId: Int): LiveData<MutableList<EventModel>> {

        return roomDbInstance().eventModelDao().getDataByCategoryId(catId)

    }

    fun getComEventDataByCategoryId(catId: Int): LiveData<MutableList<CompletedEvent>> {

        return roomDbInstance().eventModelDao().getComEventDataByCategoryId(catId)

    }

    fun roomDbInstance(): AppDatabase {
        return appDatabase!!
    }


}