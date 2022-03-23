package com.project.mediasearchapp.itemview.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.mediasearchapp.databinding.ImageItemViewBinding
import com.project.mediasearchapp.itemview.presentation.viewmodel.IImageItemViewModel
import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData

abstract class ImageItemViewAdapter(private val viewModel: IImageItemViewModel) : RecyclerView.Adapter<ImageItemViewHolder>() {


    protected val dataList by lazy { mutableListOf<ImageItemViewData>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder {
        return ImageItemViewHolder(ImageItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false), viewModel)
    }

    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) {
        if (!dataList.isNullOrEmpty() && position < dataList.size) {
            holder.onBind(dataList[position])
        }
    }

    override fun getItemCount() = dataList.size

    fun setDataList(list: List<ImageItemViewData>?) {
        list?.let {
            val addIndex = dataList.size - 1
            dataList.addAll(it)
            notifyItemRangeInserted(addIndex, list.size - 1)
        } ?: run {
            dataList.clear()
            notifyDataSetChanged()
        }
    }
}