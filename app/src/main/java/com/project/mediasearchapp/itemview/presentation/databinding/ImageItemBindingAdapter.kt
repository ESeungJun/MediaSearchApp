package com.project.mediasearchapp.itemview.presentation.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.project.mediasearchapp.utils.GlideImageLoadData
import com.project.mediasearchapp.utils.GlideUtils

object ImageItemBindingAdapter {

    @JvmStatic
    @BindingAdapter("set_image_url")
    fun ImageView.setImageUrl(url: String?) {
        if (!url.isNullOrEmpty()) {
            GlideUtils.instance.displayImage(GlideImageLoadData(this, url))
        }
    }
}