package com.easycoder.simpletask.ui.features.completed_event

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easycoder.simpletask.R
import com.easycoder.simpletask.core.interfaces.ComEventItemActionListener
import com.easycoder.simpletask.core.interfaces.EventTouchHelperListener
import com.easycoder.simpletask.data.completed_event.CompletedEvent
import com.easycoder.simpletask.utils.DateTimeUtils
import com.easycoder.simpletask.utils.TimerView
import com.github.vipulasri.timelineview.TimelineView
import com.google.android.material.card.MaterialCardView

class CompletedEventsAdapter internal constructor(private val mContext: Context) :
    RecyclerView.Adapter<CompletedEventsAdapter.ViewHolder>(),
    EventTouchHelperListener {
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mEventItemActionListener: ComEventItemActionListener? = null
    private var mCompletedEventList: MutableList<CompletedEvent>? = null
    override fun onItemSwipeToStart(position: Int) {
        mEventItemActionListener!!.onItemSwiped(mCompletedEventList!![position])
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = mInflater.inflate(R.layout.item_event, parent, false)
        return ViewHolder(
            itemView,
            viewType
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        if (mCompletedEventList != null) {
            val current = mCompletedEventList!![position]
            holder.bind(current)
            if (mEventItemActionListener != null) {
                holder.mCard.setOnClickListener { mEventItemActionListener!!.onItemClicked(current) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun getItemCount(): Int {
        return if (mCompletedEventList != null) {
            mCompletedEventList!!.size
        } else {
            0
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        holder.mTimer.stop()
    }

    fun setEvents(events: MutableList<CompletedEvent>?) {
        mCompletedEventList = events
        notifyDataSetChanged()
    }

    fun getEvents(): MutableList<CompletedEvent>? {
        return mCompletedEventList
    }

    fun setEventItemActionListener(listener: ComEventItemActionListener?) {
        mEventItemActionListener = listener
    }

    inner class ViewHolder(itemView: View, type: Int) :
        RecyclerView.ViewHolder(itemView) {
        private var mEvent: CompletedEvent? = null
        private val mTitle: TextView
        private val mTimerContainer: View
        val mTimer: TimerView
        val mCard: MaterialCardView
        private val mDueDate: TextView
        private val mMarker: TimelineView
        fun bind(event: CompletedEvent) {
            mEvent = event
            mDueDate.text = """
                ${DateTimeUtils.longToString(mEvent!!.endDate, DateTimeUtils.TIME)}
                ${DateTimeUtils.longToString(mEvent!!.endDate, DateTimeUtils.DATE)}
                """.trimIndent()
            mTitle.text = event.title
            mTimer.visibility = View.GONE
        }

        init {
            mCard = itemView.findViewById(R.id.event_card)
            mTitle = itemView.findViewById(R.id.event_title)
            mTimerContainer = itemView.findViewById(R.id.event_timer_container)
            mTimer = itemView.findViewById(R.id.event_timer)
            mDueDate = itemView.findViewById(R.id.event_due_date)
            mMarker = itemView.findViewById(R.id.event_marker)
            mMarker.initLine(type)
        }
    }

}