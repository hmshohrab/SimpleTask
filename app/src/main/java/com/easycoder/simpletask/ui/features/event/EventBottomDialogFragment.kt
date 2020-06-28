package com.easycoder.simpletask.ui.features.event


import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentManager
import com.easycoder.simpletask.R
import com.easycoder.simpletask.core.interfaces.SimpleCallback
import com.easycoder.simpletask.data.event.EventModel
import com.easycoder.simpletask.ui.features.main.MainActivity
import com.easycoder.simpletask.utils.DateTimeUtils
import com.easycoder.simpletask.utils.StateType
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.add_event_bottom_sheet.*


/**
 * Created by HM SHOHRAB on 31,May,2020
 * easyCoder company,
 * Dhaka, Bangladesh.
 * hmshohrabpc@gmail.com
 */


class EventBottomDialogFragment : BottomSheetDialogFragment() {
    private var name: String? = null
    private var notes: String? = null
    var mListener: ItemClickListener? = null
    var endDateLong: Long? = null


    fun setItemClickListener(listener: ItemClickListener) {
        this.mListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_event_bottom_sheet, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val arg = arguments

        val catId = arg?.getInt(CAT_ID)
        var eventModel: EventModel? = null
        if (catId == 0) {
            eventModel = arg.getSerializable(EVENT_MODEL) as EventModel
            edtInputTitleId.setText(eventModel.title)
            edtInputDateId.setText(
                DateTimeUtils.longToString(
                    eventModel.endDate,
                    DateTimeUtils.MEDIUM
                )
            )
            edtInputNotesId.setText(eventModel.note)
            endDateLong = eventModel.endDate
            btnUpdateEventId.visibility = View.VISIBLE
        }

        btnUpdateEventId.setOnClickListener {
            name = edtInputTitleId.text.toString()
            notes = edtInputNotesId.text.toString()


            if (formValidation()) {

                mListener?.onItemClick(
                    EventModel(
                        name!!,
                        notes!!,
                        0,
                        endDateLong!!,
                        StateType.ONGOING,
                        false,
                        0,
                        eventModel!!.id,
                        eventModel.catId,
                        0,
                        "",
                        System.currentTimeMillis()
                    )
                )
                dismiss()
            }


        }
        val eventId = System.currentTimeMillis().toString()


        edtDateLayoutId.setEndIconOnClickListener {
            val date: Long = endDateLong ?: DateTimeUtils.getCurrentTimeWithoutSec()

            val fm: FragmentManager = requireActivity().supportFragmentManager
            val dialogFragment: DateDialogFragment =
                DateDialogFragment.newInstance(
                    date,
                    false
                )
            dialogFragment.show(fm, MainActivity.DIALOG_DATE)

            dialogFragment.setDatePickListener(object : SimpleCallback<Long> {
                override fun callback(date2: Long) {
                    //    val date1 =  DateTimeUtils.longToCal(it)

                    if (date2 - System.currentTimeMillis() < 0) {
                        Toast.makeText(
                            context,
                            "End Date cannot be in Past",
                            Toast.LENGTH_LONG
                        ).show()

                    } else {
                        endDateLong = date2
                        edtInputDateId.setText(
                            DateTimeUtils.longToString(
                                date2,
                                DateTimeUtils.MEDIUM
                            )
                        )
                    }
                }
            })
        }



        btnAddEventId.setOnClickListener {
            name = edtInputTitleId.text.toString()
            notes = edtInputNotesId.text.toString()

            if (formValidation()) {

                mListener?.onItemClick(
                    EventModel(
                        name!!,
                        notes!!,
                        0,
                        endDateLong!!,
                        StateType.ONGOING,
                        false,
                        0,
                        eventId,
                        catId.toString(),
                        0,
                        "",
                        System.currentTimeMillis()
                    )
                )
                dismiss()
            }


        }


    }

    private fun formValidation(): Boolean {
        if (edtInputTitleId.text.toString().isEmpty()) {
            edtInputTitleId.error = "Event Title isn't Empty."
            edtInputTitleId.requestFocus()
            return false
        }
        if (endDateLong.toString().isEmpty() || endDateLong == null) {
            edtInputDateId.error = "Event Date isn't Empty."
            edtInputDateId.requestFocus()
            return false
        }
        if (edtInputNotesId.text.toString().isEmpty()) {
            edtInputNotesId.error = "Event Notes isn't Empty."
            edtInputNotesId.requestFocus()
            return false
        }
        return true
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay
            .getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    /*    @NonNull
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val dialog = super.onCreateDialog(savedInstanceState)
            dialog.setOnShowListener { dialogInterface ->
                val bottomSheetDialog = dialogInterface as BottomSheetDialog
                setupFullHeight(bottomSheetDialog)
            }
            return dialog
        }*/
    override fun onStart() {
        super.onStart()

        val dialog = dialog
        var bottomSheet: View? = null
        if (dialog != null) {
            bottomSheet = dialog.findViewById(R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        val view = view
        view!!.post {
            val parent = view.parent as View
            val params =
                parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            bottomSheetBehavior!!.peekHeight = view.measuredHeight
            (bottomSheet?.parent as View).setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet)
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet!!)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = ViewGroup.LayoutParams.MATCH_PARENT
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    interface ItemClickListener {
        fun onItemClick(eventModel: EventModel)
    }

    companion object {
        const val TAG = "ActionBottomDialog"
        const val CAT_ID = "CAT_ID"
        const val EVENT_MODEL = "EVENT_MODEL"
        fun newInstance(eventModel: EventModel?, catId: Int): EventBottomDialogFragment {
            return EventBottomDialogFragment()
                .apply {
                    arguments = Bundle().apply {
                        if (eventModel != null) {
                            putSerializable(EVENT_MODEL, eventModel)
                        } else {
                            putInt(CAT_ID, catId)
                        }
                    }
                }

        }
    }
}
