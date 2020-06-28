package com.easycoder.simpletask.ui.features.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easycoder.simpletask.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.add_bottom_sheet.*


/**
 * Created by HM SHOHRAB on 27,June,2020
 * easyCoder company,
 * Dhaka, Bangladesh.
 * hmshohrabpc@gmail.com
 * Let's start coding :)
 * Bismillah Hir Rahman Nir Raheem
 */


class AddBottomDialogFragment : BottomSheetDialogFragment() {

    var mListener: ItemClickListener? = null

    fun setItemClickListener(listener: ItemClickListener) {
        this.mListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_bottom_sheet, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        btnAddTaskId.setOnClickListener {
            if (edtInputTaskId.text.toString().isNotEmpty()) {
                mListener?.onItemClick(edtInputTaskId.text.toString())
                dismiss()
            } else {
                edtInputTaskId.requestFocus()
                edtInputTaskId.error = "Task Title isn't Empty."
            }


        }

    }


    interface ItemClickListener {
        fun onItemClick(item: String)
    }

    companion object {
        const val TAG = "ActionBottomDialog"
        fun newInstance(): AddBottomDialogFragment {
            return AddBottomDialogFragment()
        }
    }
}
