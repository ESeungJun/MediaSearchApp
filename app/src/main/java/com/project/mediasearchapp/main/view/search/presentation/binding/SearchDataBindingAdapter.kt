package com.project.mediasearchapp.main.view.search.presentation.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.mediasearchapp.main.view.search.presentation.data.SearchViewData
import com.project.mediasearchapp.main.view.search.presentation.view.SearchResultAdapter
import com.project.mediasearchapp.main.view.search.presentation.viewmodel.SearchViewModel

object SearchDataBindingAdapter {


    @JvmStatic
    @BindingAdapter("search_vm")
    fun RecyclerView.setInitView(viewModel: SearchViewModel) {
        layoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.VERTICAL
        }

        adapter ?: SearchResultAdapter(viewModel).apply {
            adapter = this
        }
    }

    @JvmStatic
    @BindingAdapter("search_data_list")
    fun RecyclerView.setSearchDataList(list: List<SearchViewData>?) {
        (adapter as? SearchResultAdapter)?.setDataList(list)
    }
}