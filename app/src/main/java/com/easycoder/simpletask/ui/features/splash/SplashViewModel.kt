package com.easycoder.simpletask.ui.features.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.easycoder.simpletask.core.ui.BaseViewModel

/**
 * Created by HM SHOHRAB on 26,June,2020
 * easyCoder company,
 * Dhaka, Bangladesh.
 * hmshohrabpc@gmail.com
 * Let's start coding :)
 * Bismillah Hir Rahman Nir Raheem
 */
class SplashViewModel(application: Application) : BaseViewModel(application) {

    val firstTime = MutableLiveData<Boolean>()

}