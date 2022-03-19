package com.project.mediasearchapp.common.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.project.mediasearchapp.common.utils.GlideImageLoadData
import com.project.mediasearchapp.common.utils.GlideUtils

object ImageDataBindingAdapter {

    @JvmStatic
    @BindingAdapter("set_image_url")
    fun ImageView.setImageUrl(url: String?) {
        GlideUtils.instance.displayImage(GlideImageLoadData(this, url))
    }
}