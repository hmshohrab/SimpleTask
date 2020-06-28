package com.easycoder.simpletask.ui.features.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.easycoder.simpletask.R
import com.easycoder.simpletask.core.ui.BaseActivity
import com.easycoder.simpletask.data.main.model.TaskModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>() {
    var addPhotoBottomDialogFragment: AddBottomDialogFragment? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setupActionBar(toolBarId, false)
        setupActionBar(toolBarId, false)

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayoutId,
            toolBarId,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayoutId.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navView.itemIconTintList = null
        navListener()


        loadFragment(MainFragment())
    }

    private fun navListener() {
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navCreateTaskId -> {
                    showBottomSheet()
                    drawerLayoutId.closeDrawer(GravityCompat.START)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(
                fragmentContainer.id,
                fragment
            )
            .commit()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                Toast.makeText(this, "hello world", Toast.LENGTH_LONG).show()
                true
            }
            R.id.optionStarId -> {
                Toast.makeText(this, "hello world", Toast.LENGTH_LONG).show()
                true
            }
            R.id.optionShareId -> {
                actionShare()
                Toast.makeText(this, "hello world", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun actionShare() {
        val intent = Intent()
        intent.action = "android.intent.action.SEND"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val stringBuilder = getString(R.string.shareMessage) +
                applicationContext.packageName
        intent.putExtra("android.intent.extra.TEXT", stringBuilder)
        intent.type = getString(R.string.text_plain)
        intent.putExtra("android.intent.extra.SUBJECT", "Hi, Check Out Me!")
        startActivity(intent)
    }


    fun showBottomSheet() {
        addPhotoBottomDialogFragment =
            AddBottomDialogFragment.newInstance()
        addPhotoBottomDialogFragment?.show(
            supportFragmentManager,
            AddBottomDialogFragment.TAG
        )


        addPhotoBottomDialogFragment?.setItemClickListener(object :
            AddBottomDialogFragment.ItemClickListener {
            override fun onItemClick(item: String) {
                //appDatabase.taskModelDao().insertdata(TaskModel(item, 0))
                val taskModel =
                    TaskModel(item, 0)
                val lo = viewModel.insertData(taskModel)
                if (lo >= -1) {
                    Toast.makeText(this@MainActivity, "Successfully Created.", Toast.LENGTH_LONG)
                        .show()
                }
            }

        })
    }

    override fun onBackPressed() {
        when {
            drawerLayoutId.isDrawerOpen(GravityCompat.END) -> {
                drawerLayoutId.closeDrawer(GravityCompat.END)
            }
            drawerLayoutId.isDrawerOpen(GravityCompat.START) -> {
                drawerLayoutId.closeDrawer(GravityCompat.START)
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    companion object {
        val DIALOG_DATE = "DATE"
    }
}
