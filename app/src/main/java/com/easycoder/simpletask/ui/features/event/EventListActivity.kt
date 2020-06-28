package com.easycoder.simpletask.ui.features.event

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easycoder.simpletask.R
import com.easycoder.simpletask.core.data.localDB.AppDatabase
import com.easycoder.simpletask.core.interfaces.EventItemActionListener
import com.easycoder.simpletask.core.ui.BaseActivity
import com.easycoder.simpletask.data.completed_event.CompletedEvent
import com.easycoder.simpletask.data.event.EventModel

import com.easycoder.simpletask.ui.features.completed_event.CompletedEventListActivity
import kotlinx.android.synthetic.main.activity_event_list.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.custom_toolbar.view.*

class EventListActivity : BaseActivity<EventViewModel>() {

    lateinit var eventRc: RecyclerView
    lateinit var mToolbar: Toolbar
    lateinit var appDatabase: AppDatabase

    var addEventBottomDialogFragment: EventBottomDialogFragment? = null


    var mEventsAdapter: EventsAdapter? = null
    var catId: Int? = 0
    override fun getLayoutId(): Int {
        return R.layout.activity_event_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventRc = rcEventListId
        mToolbar = toolBar.toolBarId
        setupActionBar(toolBarId, true)
        supportActionBar?.title = "Event List"
        val arg = intent.extras
        catId = arg?.getInt(EventBottomDialogFragment.CAT_ID)
        //viewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        appDatabase = viewModel.roomDbInstance()


        setRecyclerView()
        btnNewEventId.setOnClickListener {
            showBottomSheet(null)
        }

        observer()

    }

    private fun setRecyclerView() {


        eventRc.layoutManager = LinearLayoutManager(this)
        mEventsAdapter =
            EventsAdapter(this)
        mEventsAdapter?.setEvents(mutableListOf())
        mEventsAdapter?.setEventItemActionListener(object : EventItemActionListener {


            override fun onItemSwiped(eventModel: EventModel?) {
                deleteEvent(eventModel!!)
            }

            override fun onItemClicked(eventModel: EventModel?) {
                showBottomSheet(eventModel)
            }

        })
        eventRc.adapter = mEventsAdapter

        val touchHelper = ItemTouchHelper(
            EventTouchHelperCallback(
                mEventsAdapter!!
            )
        )
        touchHelper.attachToRecyclerView(eventRc)
        val animationController =
            AnimationUtils.loadLayoutAnimation(this, R.anim.layout_fall_down)
        eventRc.layoutAnimation = animationController


    }

    /*    private fun filterCompletedEvents(eventList: MutableList<EventModel>) {
            val iterator = eventList.iterator()
            while (iterator.hasNext()) {
                val event = iterator.next()
                if (event.endDate < System.currentTimeMillis()) {
                    deleteEvent(event)
                }
            }
        }*/
    private fun observer() {
        viewModel.getEventList(catId!!).observe(this, Observer {
            //   filterCompletedEvents(it)
            mEventsAdapter?.setEvents(it)
        })
    }


    private fun deleteEvent(event_model: EventModel) {
        addToCompletedEvents(event_model)

        val isSuccess = viewModel.deleteEventData(event_model)

        if (isSuccess == 1) {
            Toast.makeText(
                this@EventListActivity,
                "Successfully Removed.",
                Toast.LENGTH_LONG
            ).show()
        }

    }


    private fun addToCompletedEvents(event_model: EventModel) {
        val completedEvent =
            CompletedEvent(
                event_model.title,
                event_model.note,
                event_model.startDate,
                event_model.endDate,
                event_model.state,
                event_model.isDurableEvent,
                event_model.priority,
                event_model.id,
                event_model.catId,
                event_model.reminder,
                event_model.category,
                event_model.creationDate
            )
        viewModel.insertCompleteEventData(completedEvent)
    }


    private fun showBottomSheet(event_model: EventModel?) {
        addEventBottomDialogFragment =
            EventBottomDialogFragment.newInstance(event_model, catId!!)
        addEventBottomDialogFragment?.show(
            this.supportFragmentManager,
            EventBottomDialogFragment.TAG
        )


        addEventBottomDialogFragment?.setItemClickListener(object :
            EventBottomDialogFragment.ItemClickListener {
            override fun onItemClick(eventModel: EventModel) {


                //new event create...
                if (event_model == null) {
                    val lo = viewModel.insertData(eventModel)
                    if (lo >= -1) {
                        val success = viewModel.updateCatData(catId!!)
                        Toast.makeText(
                            this@EventListActivity,
                            "Successfully Created.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {

                    val dd = viewModel.updateEventData(eventModel)
                    Toast.makeText(
                        this@EventListActivity,
                        "Successfully Updated.$dd",
                        Toast.LENGTH_LONG
                    ).show()

                }

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.event_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.optionComEventId -> {
                val bundle = Bundle()
                bundle.putInt(EventBottomDialogFragment.CAT_ID, catId!!)
                startActivity(CompletedEventListActivity::class.java, bundle)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
