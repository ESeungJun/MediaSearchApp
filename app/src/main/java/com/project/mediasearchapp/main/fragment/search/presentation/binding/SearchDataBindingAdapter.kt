package com.project.mediasearchapp.main.fragment.search.presentation.binding

import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.mediasearchapp.utils.EndlessRecyclerOnScrollListener
import com.project.mediasearchapp.main.fragment.search.presentation.data.SearchActionEvent
import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData
import com.project.mediasearchapp.itemview.presentation.viewmodel.IImageItemViewModel
import com.project.mediasearchapp.main.fragment.search.presentation.view.SearchResultAdapter
import com.project.mediasearchapp.main.fragment.search.presentation.viewmodel.SearchViewModel

object SearchDataBindingAdapter {

    @JvmStatic
    @BindingAdapter("search_item_vm", "search_vm")
    fun RecyclerView.setInitView(itemVM: IImageItemViewModel, searchVM: SearchViewModel) {
        layoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.VERTICAL
        }

        adapter ?: SearchResultAdapter(itemVM).apply {
            adapter = this
        }

        val scrollListener = object : EndlessRecyclerOnScrollListener(layoutManager as? LinearLayoutManager) {
            override fun onLoadMore(nextPage: Int) {
                searchVM.moreLoadSearchResult(nextPage)
            }
        }
        addOnScrollListener(scrollListener)

        findViewTreeLifecycleOwner()?.let { lifeCycleOwner ->
            searchVM.searchActionEvent.observe(lifeCycleOwner) {
                if (it is SearchActionEvent.OnInitSearchEvent) {
                    (adapter as? SearchResultAdapter)?.setDataList(null)
                    scrollListener.resetState()
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("search_data_list")
    fun RecyclerView.setSearchDataList(list: List<ImageItemViewData>?) {
        (adapter as? SearchResultAdapter)?.setDataList(list)
    }
}