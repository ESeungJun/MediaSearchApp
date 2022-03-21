package com.project.mediasearchapp.main.fragment.favorite.presentation.view

import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData
import com.project.mediasearchapp.itemview.presentation.view.ImageItemViewAdapter
import com.project.mediasearchapp.itemview.presentation.viewmodel.IImageItemViewModel

class FavoriteAdapter(viewModel: IImageItemViewModel) : ImageItemViewAdapter(viewModel) {

    override fun setDataList(list: List<ImageItemViewData>?) {
        if (!list.isNullOrEmpty()) {
            dataList.clear()
            dataList.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun notifyRemoveItem(data: ImageItemViewData) {
        val removeIndex = dataList.indexOf(dataList.find { it.imageUrl == data.imageUrl })
        dataList.removeAt(removeIndex)
        notifyItemRemoved(removeIndex)
    }

    fun notifyAddItem(data: ImageItemViewData) {
        dataList.add(0, data)
        notifyItemInserted(0)
    }
}