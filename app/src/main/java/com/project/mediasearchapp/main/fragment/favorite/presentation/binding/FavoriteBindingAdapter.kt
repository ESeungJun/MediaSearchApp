package com.project.mediasearchapp.main.fragment.favorite.presentation.binding

import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.mediasearchapp.main.fragment.favorite.presentation.data.FavoriteEventData
import com.project.mediasearchapp.main.fragment.favorite.presentation.view.FavoriteAdapter
import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData
import com.project.mediasearchapp.itemview.presentation.viewmodel.IImageItemViewModel

object FavoriteBindingAdapter {

    @JvmStatic
    @BindingAdapter("favorite_item_vm")
    fun RecyclerView.setInitView(viewModel: IImageItemViewModel) {
        layoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.VERTICAL
        }

        adapter ?: FavoriteAdapter(viewModel).apply {
            adapter = this
        }
    }

    @JvmStatic
    @BindingAdapter("favorite_data_list")
    fun RecyclerView.setFavoriteDataList(list: List<ImageItemViewData>?) {
        (adapter as? FavoriteAdapter)?.setDataList(list)
    }
}