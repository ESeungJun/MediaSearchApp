package com.project.mediasearchapp.main.view.search.presentation.binding

import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.mediasearchapp.common.utils.EndlessRecyclerOnScrollListener
import com.project.mediasearchapp.main.view.search.presentation.data.SearchActionEvent
import com.project.mediasearchapp.main.view.search.presentation.data.SearchViewData
import com.project.mediasearchapp.main.view.search.presentation.view.SearchResultAdapter
import com.project.mediasearchapp.main.view.search.presentation.viewmodel.SearchViewModel

object SearchDataBindingAdapter {

    @JvmStatic
    @BindingAdapter("search_init")
    fun RecyclerView.setInitView(viewModel: SearchViewModel) {
        layoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.VERTICAL
        }

        adapter ?: SearchResultAdapter(viewModel).apply {
            adapter = this
        }

        val scrollListener = object : EndlessRecyclerOnScrollListener(layoutManager as? LinearLayoutManager) {
            override fun onLoadMore(nextPage: Int) {
                viewModel.moreLoadSearchResult(nextPage)
            }
        }
        addOnScrollListener(scrollListener)

        findViewTreeLifecycleOwner()?.let { lifeCycleOwner ->
            viewModel.searchActionEvent.observe(lifeCycleOwner) {
                if (it is SearchActionEvent.OnInitSearchEvent) {
                    (adapter as? SearchResultAdapter)?.setDataList(null)
                    scrollListener.resetState()
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("search_data_list")
    fun RecyclerView.setSearchDataList(list: List<SearchViewData>?) {
        (adapter as? SearchResultAdapter)?.setDataList(list)
    }
}