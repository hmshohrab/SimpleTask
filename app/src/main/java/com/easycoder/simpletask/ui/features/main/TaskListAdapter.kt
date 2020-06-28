package com.easycoder.simpletask.ui.features.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.easycoder.simpletask.R
import com.easycoder.simpletask.core.interfaces.SimpleCallback
import com.easycoder.simpletask.core.interfaces.SimplestCallback
import com.easycoder.simpletask.data.main.model.TaskModel
import kotlinx.android.synthetic.main.raw_add_items.view.*
import kotlinx.android.synthetic.main.raw_items.view.*
import kotlinx.android.synthetic.main.raw_items.view.txtTitleId


/**
 * Created by HM SHOHRAB on 26,June,2020
 * easyCoder company,
 * Dhaka, Bangladesh.
 * hmshohrabpc@gmail.com
 * Let's start coding :)
 * Bismillah Hir Rahman Nir Raheem
 */

class TaskListAdapter(private val taskModelList: List<TaskModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ADD_TYPE_TASK = 1
    private val ORG_TYPE_TASK = 2
    var mListener: SimplestCallback? = null
    var mListener1: SimpleCallback<TaskModel>? = null

    fun setAddListener(listener: SimplestCallback) {
        this.mListener = listener
    }

    fun setOrgListener(listener: SimpleCallback<TaskModel>) {
        this.mListener1 = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return if (viewType == ADD_TYPE_TASK) {
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.raw_add_items, parent, false)

            AddViewHolder(view) //object of TextPostViewHolder will return
        } else {
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.raw_items, parent, false)

            MyViewHolder(view) //object of ImagePostViewHolder will return
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        //
        val context = holder.itemView.context

        if (holder.itemViewType == ADD_TYPE_TASK) {
            val viewHolder = holder as AddViewHolder
            viewHolder.itemView.setOnClickListener {
                mListener?.justSayMe()
            }

            // viewHolder.profileName.text = postData.userName
            //  Glide.with(context).load(postData.userProfilePhotoUrl).into(viewHolder.profilePhoto)
            //   viewHolder.timeStamp.text = postData.timeStamp
            //  viewHolder.postDescription.text = postData.postDescription


        } else {
            val viewHolder = holder as MyViewHolder
            val taskModel = taskModelList[position]

            viewHolder.txtTitle.text = taskModel.title
            viewHolder.txtTotalItem.text = taskModel.tItems.toString() + " Items"
            //viewHolder.profileName.text = postData.userName
            //Glide.with(context).load(postData.userProfilePhotoUrl).into(viewHolder.profilePhoto)
            //viewHolder.timeStamp.text = postData.timeStamp
            // viewHolder.postDescription.text = postData.postDescription

            // Glide.with(holder.itemView.context).load(postData.postImageUrl)
            //     .into(viewHolder.imageView)

            viewHolder.itemView.setOnClickListener { mListener1?.callback(taskModel) }


        }
    }

    override fun getItemCount(): Int {
        return taskModelList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position != taskModelList.size) ORG_TYPE_TASK else ADD_TYPE_TASK
    }

    inner class MyViewHolder(view: View?) :
        RecyclerView.ViewHolder(view!!) {
        val txtTitle = itemView.txtTitleId
        val txtTotalItem = itemView.txtTotalItemsId


    }


    inner class AddViewHolder(view: View?) :
        RecyclerView.ViewHolder(view!!) {
        val imgAdd = itemView.imgAddId
    }
}