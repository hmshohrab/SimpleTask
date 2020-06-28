package com.easycoder.simpletask.utils

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import com.easycoder.simpletask.core.data.preference.AppPreference
import com.easycoder.simpletask.core.data.preference.AppPreferenceImpl

object GeneralHelper {

    fun hideStatusBar(activity: Activity) {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    fun getSharedPreference(appContext: Context): AppPreference {
        return AppPreferenceImpl(appContext)
    }


}