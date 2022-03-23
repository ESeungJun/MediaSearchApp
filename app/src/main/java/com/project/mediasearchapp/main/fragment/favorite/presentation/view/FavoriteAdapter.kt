package com.project.mediasearchapp.main.fragment.favorite.presentation.view

import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData
import com.project.mediasearchapp.itemview.presentation.view.ImageItemViewAdapter
import com.project.mediasearchapp.itemview.presentation.viewmodel.IImageItemViewModel

class FavoriteAdapter(viewModel: IImageItemViewModel) : ImageItemViewAdapter(viewModel) {

    fun notifyRemoveItem(data: ImageItemViewData) {
        val removeIndex = dataList.indexOf(dataList.find { it.imageUrl == data.imageUrl })

        if (removeIndex < dataList.size) {
            dataList.removeAt(removeIndex)
            notifyItemRemoved(removeIndex)
        }
    }

    fun notifyAddItem(data: ImageItemViewData) {
        dataList.add(0, data)
        notifyItemInserted(0)
    }
}