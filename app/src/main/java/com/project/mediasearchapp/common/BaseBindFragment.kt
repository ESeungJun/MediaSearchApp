package com.project.mediasearchapp.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseBindFragment<VIEW: ViewDataBinding, VM: ViewModel>(@LayoutRes private val layoutRes: Int) : Fragment() {

    abstract fun createViewModel(): VM
    abstract fun initFragment(dataBinding: VIEW, viewModel: VM)

    private lateinit var _dataBinding: VIEW
    private lateinit var _viewModel: VM

    protected val viewModel: VM?
        get() = if (::_viewModel.isInitialized) {
            _viewModel
        } else {
            null
        }

    protected val dataBinding: VIEW?
        get() = if (::_dataBinding.isInitialized) {
            _dataBinding
        } else {
            null
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _dataBinding = DataBindingUtil.inflate<VIEW>(inflater, layoutRes, container, false)
        _viewModel = createViewModel()

        _dataBinding.apply {
            setVariable(BR.viewModel, _viewModel)
            lifecycleOwner = this@BaseBindFragment
        }

        return _dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (::_dataBinding.isInitialized && ::_viewModel.isInitialized) {
            initFragment(_dataBinding, _viewModel)
        }
    }
}