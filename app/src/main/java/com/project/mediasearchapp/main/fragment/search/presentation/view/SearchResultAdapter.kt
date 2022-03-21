package com.project.mediasearchapp.main.fragment.search.presentation.view

import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData
import com.project.mediasearchapp.itemview.presentation.view.ImageItemViewAdapter
import com.project.mediasearchapp.itemview.presentation.viewmodel.IImageItemViewModel
import com.project.mediasearchapp.main.fragment.search.presentation.viewmodel.SearchViewModel

class SearchResultAdapter(viewModel: IImageItemViewModel) : ImageItemViewAdapter(viewModel) {

    override fun setDataList(list: List<ImageItemViewData>?) {
        dataList.clear()
        list?.let { dataList.addAll(it) }
        notifyDataSetChanged()
    }

    fun notifyItemStatus(data: ImageItemViewData) {
        dataList.find { it == data }?.let { findItem ->
            findItem.isFavorite = data.isFavorite
            notifyItemChanged(dataList.indexOf(findItem), findItem)
        }
    }
}