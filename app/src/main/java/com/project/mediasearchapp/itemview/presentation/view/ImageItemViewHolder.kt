package com.project.mediasearchapp.itemview.presentation.view

import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.project.mediasearchapp.databinding.ImageItemViewBinding
import com.project.mediasearchapp.itemview.presentation.viewmodel.IImageItemViewModel
import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData

class ImageItemViewHolder(
    private val itemViewBinding: ImageItemViewBinding,
    viewModel: IImageItemViewModel
) : RecyclerView.ViewHolder(itemViewBinding.root) {

    init {
        itemViewBinding.setVariable(BR.viewModel, viewModel)
    }


    fun onBind(data: ImageItemViewData?) {
        itemViewBinding.setVariable(BR.viewData, data)
        itemViewBinding.executePendingBindings()
    }
}