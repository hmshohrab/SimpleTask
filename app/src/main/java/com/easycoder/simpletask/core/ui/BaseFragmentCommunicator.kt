package com.easycoder.simpletask.core.ui

import android.os.Bundle
import androidx.appcompat.widget.Toolbar

interface BaseFragmentCommunicator {

    fun startActivity(clz: Class<*>?, bundle: Bundle?)
    fun setupActionBar(toolbar: Toolbar, enableBackButton: Boolean)
}