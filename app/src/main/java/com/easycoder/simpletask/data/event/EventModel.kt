package com.easycoder.simpletask.data.event


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "Event_table")
class EventModel(
    val title: String,
    val note: String,
    val startDate: Long,
    val endDate: Long,
    val state: Int,
    val isDurableEvent: Boolean,
    val priority: Int,
    val id: String,
    val catId: String,
    val reminder: Int,
    val category: String?,
    val creationDate: Long
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var e_id: Int = 0

    //Use this constructor to create a new active Event.
    constructor(
        title: String,
        note: String,
        startDate: Long,
        endDate: Long,
        state: Int,
        durableEvent: Boolean,
        priority: Int,
        catId: String,
        reminder: Int,
        category: String?
    ) : this(
        title,
        note,
        startDate,
        endDate,
        state,
        durableEvent,
        priority,
        UUID.randomUUID().toString(),
        catId,
        reminder,
        category,
        System.currentTimeMillis()
    )

}