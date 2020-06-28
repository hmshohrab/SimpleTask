package com.easycoder.simpletask.ui.features.event

import android.app.Activity
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.easycoder.simpletask.R
import com.easycoder.simpletask.core.interfaces.SimpleCallback
import com.easycoder.simpletask.utils.DateTimeUtils
import java.util.*

class DateDialogFragment : DialogFragment() {
    private var mCalendar: Calendar? = null
    private var extraDate: Long = 0
    private var isDatePicker = true

    private var mListener: SimpleCallback<Long>? = null

    fun setDatePickListener(listener: SimpleCallback<Long>) {
        mListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val argDate = arguments!!.getLong(ARG_DATE)
        val argOnlyTimePicker =
            arguments!!.getBoolean(ARG_ONLY_TIME_PICKER)
        mCalendar = if (argDate == 0L) {
            Calendar.getInstance()
        } else {
            DateTimeUtils.longToCal(argDate)
        }
        val view =
            LayoutInflater.from(activity).inflate(R.layout.dialog_date, null)
        val datePicker =
            view.findViewById<DatePicker>(R.id.dialog_date)
        val timePicker = view.findViewById<TimePicker>(R.id.dialog_time)
        datePicker.init(
            mCalendar!!.get(Calendar.YEAR),
            mCalendar!!.get(Calendar.MONTH),
            mCalendar!!.get(Calendar.DAY_OF_MONTH),
            null
        )
        timePicker.setIs24HourView(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.hour = mCalendar!!.get(Calendar.HOUR_OF_DAY)
            timePicker.minute = mCalendar!!.get(Calendar.MINUTE)
        } else {
            timePicker.currentHour = mCalendar!!.get(Calendar.HOUR_OF_DAY)
            timePicker.currentMinute = mCalendar!!.get(Calendar.MINUTE)
        }
        if (argOnlyTimePicker) {
            timePicker.visibility = View.VISIBLE
            datePicker.visibility = View.GONE
        }
        val dialog =
            AlertDialog.Builder(activity!!, R.style.DatePickerDialog)
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(
                    android.R.string.cancel
                ) { dialogInterface, i -> dismiss() }
                .setNeutralButton(R.string.date, null)
                .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener {
                    if (isDatePicker && !argOnlyTimePicker) {
                        mCalendar!!.set(Calendar.YEAR, datePicker.year)
                        mCalendar!!.set(Calendar.MONTH, datePicker.month)
                        mCalendar!!.set(
                            Calendar.DAY_OF_MONTH,
                            datePicker.dayOfMonth
                        )
                        datePicker.visibility = View.GONE
                        timePicker.visibility = View.VISIBLE
                        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).visibility = View.VISIBLE
                        isDatePicker = false
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            mCalendar!!.set(
                                Calendar.HOUR_OF_DAY,
                                timePicker.hour
                            )
                            mCalendar!!.set(Calendar.MINUTE, timePicker.minute)
                            Toast.makeText(
                                activity,
                                "" + timePicker.hour + "m " + timePicker.minute,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            mCalendar!!.set(
                                Calendar.HOUR_OF_DAY,
                                timePicker.currentHour
                            )
                            mCalendar!!.set(
                                Calendar.MINUTE,
                                timePicker.currentMinute
                            )
                        }
                        extraDate =
                            DateTimeUtils.calToLong(
                                mCalendar
                            )
                        sendResult(Activity.RESULT_OK, extraDate)
                        dismiss()
                    }
                }
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).visibility = View.GONE
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
                .setOnClickListener {
                    timePicker.visibility = View.GONE
                    datePicker.visibility = View.VISIBLE
                    dialog.getButton(AlertDialog.BUTTON_NEUTRAL).visibility = View.GONE
                    isDatePicker = true
                }
        }
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        return dialog
    }

    private fun sendResult(resultCode: Int, date: Long) {
        mListener?.callback(date)
    }

    companion object {
        const val EXTRA_DATE = "EXTRA_DATE"
        private const val ARG_DATE = "DATE"
        private const val ARG_ONLY_TIME_PICKER = "ONLY_TIME_PICKER"
        fun newInstance(date: Long, onlyTimePicker: Boolean): DateDialogFragment {
            val args = Bundle()
            args.putLong(ARG_DATE, date)
            args.putBoolean(ARG_ONLY_TIME_PICKER, onlyTimePicker)
            val fragment =
                DateDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}