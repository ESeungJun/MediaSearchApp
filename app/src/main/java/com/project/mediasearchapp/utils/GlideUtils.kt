package com.project.mediasearchapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideUtils {

    private fun clearView(imageView: ImageView?) {
        imageView?.let {
            Glide.with(it.context).clear(imageView)
        }
    }

    fun displayImage(data: GlideImageLoadData) {
        data.run {
            clearView(imageView)
            Glide.with(imageView.context).load(imageUrl).into(imageView)
        }
    }



    private object LazyHolder {
        val INSTANCE = GlideUtils()
    }

    companion object {
        @JvmStatic
        val instance by lazy { LazyHolder.INSTANCE }

        @JvmStatic
        fun clearMemory(context: Context?) {
            context?.let { Glide.get(context).clearMemory() }
        }
    }


}