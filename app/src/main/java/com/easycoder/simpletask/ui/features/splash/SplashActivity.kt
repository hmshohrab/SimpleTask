package com.easycoder.simpletask.ui.features.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.easycoder.simpletask.BuildConfig
import com.easycoder.simpletask.R
import com.easycoder.simpletask.core.data.localDB.AppDatabase
import com.easycoder.simpletask.core.data.preference.AppPreference
import com.easycoder.simpletask.core.interfaces.RequestCompleteListener
import com.easycoder.simpletask.core.ui.BaseActivity
import com.easycoder.simpletask.data.main.TaskRepository
import com.easycoder.simpletask.data.main.TaskRepositoryImpl
import com.easycoder.simpletask.data.main.model.TaskModel
import com.easycoder.simpletask.ui.features.main.MainActivity
import com.easycoder.simpletask.utils.GeneralHelper

class SplashActivity : BaseActivity<SplashViewModel>() {


    lateinit var taskRepository: TaskRepository
    lateinit var appDatabase: AppDatabase
    lateinit var appPreference: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDatabase = AppDatabase.getInstance(this)!!
        taskRepository = TaskRepositoryImpl(this, appDatabase)
        //  GeneralHelper.hideStatusBar(this)
        appPreference = GeneralHelper.getSharedPreference(this)
        if (appPreference.first_time) {
            appDatabase = AppDatabase.getInstance(this)!!
            taskRepository = TaskRepositoryImpl(this, appDatabase)

            taskRepository.setTaskList(object : RequestCompleteListener<MutableList<TaskModel>> {
                override fun onRequestSuccess(data: MutableList<TaskModel>) {
                    loadView()
                }

                override fun onRequestFailed(errorMessage: String) {
                    Toast.makeText(
                        this@SplashActivity,
                        "Something Error. Please Restart App.",
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
        } else {
            loadView()
        }

    }


    private fun syncNecessaryData() {

    }

    private fun loadView(versionName: String = BuildConfig.VERSION_NAME) {

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1200)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }


}
