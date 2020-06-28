package com.easycoder.simpletask.ui.features.completed_event

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easycoder.simpletask.R
import com.easycoder.simpletask.core.interfaces.ComEventItemActionListener
import com.easycoder.simpletask.core.ui.BaseActivity
import com.easycoder.simpletask.data.completed_event.CompletedEvent
import com.easycoder.simpletask.ui.features.event.EventBottomDialogFragment
import com.easycoder.simpletask.ui.features.event.EventTouchHelperCallback
import com.easycoder.simpletask.ui.features.event.EventViewModel
import kotlinx.android.synthetic.main.activity_completed_event_list.*
import kotlinx.android.synthetic.main.custom_toolbar.view.*


class CompletedEventListActivity :
    BaseActivity<EventViewModel>() {

    lateinit var rcComEvent: RecyclerView
    lateinit var mToolbar: Toolbar
    var mCompletedEventsAdapter: CompletedEventsAdapter? = null

    var catId: Int? = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rcComEvent = rcComEventId
        mToolbar = toolBar.toolBarId
        setupActionBar(mToolbar, true)
        supportActionBar?.title = "Completed Event List"
        val arg = intent.extras
        catId = arg?.getInt(EventBottomDialogFragment.CAT_ID)

        setRecyclerView()


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_completed_event_list
    }

    private fun setRecyclerView() {

        rcComEvent.layoutManager = LinearLayoutManager(this)
        mCompletedEventsAdapter =
            CompletedEventsAdapter(
                this
            )
        mCompletedEventsAdapter?.setEvents(mutableListOf())
        mCompletedEventsAdapter?.setEventItemActionListener(object : ComEventItemActionListener {
            override fun onItemSwiped(eventModel: CompletedEvent?) {
                deleteEvent(eventModel!!)
            }

            override fun onItemClicked(eventModel: CompletedEvent?) {

            }
        })
        rcComEvent.adapter = mCompletedEventsAdapter


        val touchHelper = ItemTouchHelper(
            EventTouchHelperCallback(
                mCompletedEventsAdapter!!
            )
        )
        touchHelper.attachToRecyclerView(rcComEvent)
        val animationController =
            AnimationUtils.loadLayoutAnimation(this, R.anim.layout_fall_down)
        rcComEvent.layoutAnimation = animationController


        viewModel.getComEventDataByCategoryId(catId!!).observe(this,
            Observer {
                mCompletedEventsAdapter?.setEvents(it)
            })


    }


    private fun deleteEvent(completedEvent: CompletedEvent) {

        val isSuccess = viewModel.deleteComEventData(completedEvent)

        if (isSuccess == 1) {
            viewModel.updateCatData1(catId!!)
            Toast.makeText(
                this,
                "Successfully Removed.",
                Toast.LENGTH_LONG
            ).show()
        }

    }


}