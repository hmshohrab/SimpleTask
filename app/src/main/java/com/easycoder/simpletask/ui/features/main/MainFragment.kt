package com.easycoder.simpletask.ui.features.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.easycoder.simpletask.R
import com.easycoder.simpletask.core.data.preference.AppPreference
import com.easycoder.simpletask.core.interfaces.SimpleCallback
import com.easycoder.simpletask.core.interfaces.SimplestCallback
import com.easycoder.simpletask.core.ui.BaseFragment
import com.easycoder.simpletask.data.main.model.TaskModel
import com.easycoder.simpletask.ui.features.event.EventBottomDialogFragment
import com.easycoder.simpletask.ui.features.event.EventListActivity
import com.easycoder.simpletask.utils.GeneralHelper
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : BaseFragment<MainViewModel>() {


    var addPhotoBottomDialogFragment: AddBottomDialogFragment? = null
    lateinit var appPreference: AppPreference
    var taskModelList: MutableList<TaskModel>? = null
    var taskListAdapter: TaskListAdapter? = null


    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appPreference = GeneralHelper.getSharedPreference(requireContext())


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appPreference.first_time = false

        setRecyclerView()
        observer()

    }

    private fun setRecyclerView() {
        taskModelList = mutableListOf()

        rc_taskId.layoutManager = GridLayoutManager(requireContext(), 2)
        taskListAdapter =
            TaskListAdapter(
                taskModelList!!
            )
        taskListAdapter?.setAddListener(object : SimplestCallback {
            override fun justSayMe() {
                showBottomSheet(view)
            }
        })
        taskListAdapter?.setOrgListener(object : SimpleCallback<TaskModel> {
            override fun callback(taskModel: TaskModel) {
                val bundle = Bundle()
                bundle.putInt(EventBottomDialogFragment.CAT_ID, taskModel.id)
                startActivity(EventListActivity::class.java, bundle)
            }
        })
        rc_taskId.adapter = taskListAdapter
    }

    private fun observer() {
        viewModel.getTaskList().observe(viewLifecycleOwner, Observer {

            taskModelList?.clear()
            taskModelList?.addAll(it)
            taskListAdapter?.notifyDataSetChanged()

        })

    }


    fun showBottomSheet(view: View?) {
        addPhotoBottomDialogFragment =
            AddBottomDialogFragment.newInstance()
        addPhotoBottomDialogFragment?.show(
            requireActivity().supportFragmentManager,
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
                    Toast.makeText(requireContext(), "Successfully Created.", Toast.LENGTH_LONG)
                        .show()
                }
            }

        })
    }

}