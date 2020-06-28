package com.easycoder.simpletask.ui.features.event

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.iwgang.countdownview.DynamicConfig
import com.easycoder.simpletask.R
import com.easycoder.simpletask.core.interfaces.EventItemActionListener
import com.easycoder.simpletask.core.interfaces.EventTouchHelperListener
import com.easycoder.simpletask.data.event.EventModel
import com.easycoder.simpletask.utils.DateTimeUtils
import com.easycoder.simpletask.utils.TimerView
import com.github.vipulasri.timelineview.TimelineView
import com.google.android.material.card.MaterialCardView

class EventsAdapter internal constructor(private val mContext: Context) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>(),
    EventTouchHelperListener {
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mEventItemActionListener: EventItemActionListener? = null
    private var mEvents: MutableList<EventModel>? = null
    override fun onItemSwipeToStart(position: Int) {
        mEventItemActionListener!!.onItemSwiped(mEvents!![position])
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
        if (mEvents != null) {
            val current = mEvents!![position]
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
        return if (mEvents != null) {
            mEvents!!.size
        } else {
            0
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        holder.mTimer.stop()
    }

    fun setEvents(events: MutableList<EventModel>?) {
        mEvents = events
        notifyDataSetChanged()
    }

    fun getEvents(): MutableList<EventModel>? {
        return mEvents
    }

    fun setEventItemActionListener(listener: EventItemActionListener?) {
        mEventItemActionListener = listener
    }

    inner class ViewHolder(itemView: View, type: Int) :
        RecyclerView.ViewHolder(itemView) {
        private var mEvent: EventModel? = null
        private val mTitle: TextView
        private val mTimerContainer: View
        val mTimer: TimerView
        private val mTimerState: TextView
        val mCard: MaterialCardView
        private val mDueDate: TextView
        private val mMarker: TimelineView
        fun bind(event: EventModel) {
            mEvent = event
            mDueDate.text = """
                ${DateTimeUtils.longToString(mEvent!!.endDate, DateTimeUtils.TIME)}
                ${DateTimeUtils.longToString(mEvent!!.endDate, DateTimeUtils.DATE)}
                """.trimIndent()
            refreshNormal(mEvent)
            mTitle.text = event.title
            if (event.endDate < System.currentTimeMillis()) {
                mTimer.stop()
                mEventItemActionListener!!.onItemSwiped(event)
            }
        }

        private fun refreshNormal(event: EventModel?) {
            activeEvent()
            mTimer.start(mEvent!!.endDate - System.currentTimeMillis())
            toTealTheme()
            mTimerState.setText(R.string.timer_state_to_reach)
            mTimer.setOnCountdownEndListener { mEventItemActionListener!!.onItemSwiped(event) }
        }

        private fun activeEvent() {
            mTitle.paint.flags = Paint.ANTI_ALIAS_FLAG
            mDueDate.setPadding(0, 0, 0, 0)
            mTimerContainer.visibility = View.VISIBLE
        }

        private fun toTealTheme() {
            mTitle.setTextColor(mContext.resources.getColor(R.color.white))
            mDueDate.setTextColor(mContext.resources.getColor(R.color.yellow_300))
            mCard.setCardBackgroundColor(mContext.resources.getColor(R.color.colorAccent))
            mTimerState.setTextColor(mContext.resources.getColor(R.color.white_secondary))
            val dynamicConfigBuilder = DynamicConfig.Builder()
            dynamicConfigBuilder
                .setTimeTextColor(mContext.resources.getColor(R.color.white))
                .setSuffixTextColor(mContext.resources.getColor(R.color.white_secondary))
                .setShowSecond(true)
                .setShowMinute(true)
            mTimer.dynamicShow(dynamicConfigBuilder.build())
        }

        init {
            mCard = itemView.findViewById(R.id.event_card)
            mTitle = itemView.findViewById(R.id.event_title)
            mTimerContainer = itemView.findViewById(R.id.event_timer_container)
            mTimer = itemView.findViewById(R.id.event_timer)
            mTimerState = itemView.findViewById(R.id.event_timer_state)
            mDueDate = itemView.findViewById(R.id.event_due_date)
            mMarker = itemView.findViewById(R.id.event_marker)
            //mMarker.initLine(type);
        }
    }

}