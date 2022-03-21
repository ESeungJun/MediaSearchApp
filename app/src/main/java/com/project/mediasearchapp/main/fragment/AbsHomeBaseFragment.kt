package com.project.mediasearchapp.main.fragment

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.project.mediasearchapp.common.BaseBindFragment
import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData
import com.project.mediasearchapp.main.activity.home.presentation.viewmodel.HomeActViewModel

abstract class AbsHomeBaseFragment<BINDING: ViewDataBinding, VM: ViewModel>(layoutId: Int) : BaseBindFragment<BINDING, VM>(layoutId) {
    abstract fun initFragment(dataBinding: BINDING, viewModel: VM, actViewModel: HomeActViewModel)

    private val activityViewModel: HomeActViewModel by activityViewModels()

    override fun initFragment(dataBinding: BINDING, viewModel: VM) {
        dataBinding.setVariable(BR.itemViewModel, activityViewModel)

        initFragment(dataBinding, viewModel, activityViewModel)
    }
}