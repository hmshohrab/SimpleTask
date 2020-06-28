package com.easycoder.simpletask.core.interfaces

import com.easycoder.simpletask.data.completed_event.CompletedEvent

interface ComEventItemActionListener {
    fun onItemSwiped(eventModel: CompletedEvent?)
    fun onItemClicked(eventModel: CompletedEvent?)
}