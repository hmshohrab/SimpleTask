package com.easycoder.simpletask.core.interfaces

import com.easycoder.simpletask.data.event.EventModel

interface EventItemActionListener {
    fun onItemSwiped(eventModel: EventModel?)
    fun onItemClicked(eventModel: EventModel?)
}