package com.project.mediasearchapp.main.view.search.presentation.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.project.mediasearchapp.databinding.SearchResultItemViewBinding
import com.project.mediasearchapp.main.view.search.presentation.data.SearchViewData
import com.project.mediasearchapp.main.view.search.presentation.viewmodel.ISearchResultItemViewModel

class SearchResultAdapter(private val viewModel: ISearchResultItemViewModel) :
    RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    private val dataList by lazy { mutableListOf<SearchViewData>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            SearchResultItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), viewModel
        )
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        if (!dataList.isNullOrEmpty() && position < dataList.size - 1) {
            holder.onBind(dataList[position])
        }
    }

    override fun getItemCount() = dataList.size

    fun setDataList(list: List<SearchViewData>?) {
        dataList.clear()
        list?.let { dataList.addAll(it) }
        notifyDataSetChanged()
    }


    inner class SearchResultViewHolder(
        private val itemViewBinding: SearchResultItemViewBinding,
        viewModel: ISearchResultItemViewModel
    ) : RecyclerView.ViewHolder(itemViewBinding.root) {

        init {
            itemViewBinding.setVariable(BR.viewModel, viewModel)
        }


        fun onBind(data: SearchViewData?) {
            itemViewBinding.setVariable(BR.viewData, data)
        }
    }
}