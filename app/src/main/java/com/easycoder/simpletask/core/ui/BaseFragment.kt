package com.easycoder.simpletask.core.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType


abstract class BaseFragment<ViewModel : BaseViewModel> : Fragment() {


    lateinit var viewModel: ViewModel

    private lateinit var communicator: BaseFragmentCommunicator

    abstract fun getLayoutId(): Int


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseFragmentCommunicator) {
            communicator = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(getViewModelClass())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(getLayoutId(), container, false)

        lifecycle.addObserver(viewModel)
        return view
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    fun startActivity(clz: Class<*>?, bundle: Bundle?) {
        communicator.startActivity(clz, bundle)
    }

    fun setupActionBar(toolbar: Toolbar, enableBackButton: Boolean) {
        communicator.setupActionBar(toolbar, enableBackButton)
    }

    private fun getViewModelClass(): Class<ViewModel> {
        val type =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]   // index of 0 means first argument of Base class param
        @Suppress("UNCHECKED_CAST")
        return type as Class<ViewModel>
    }
}