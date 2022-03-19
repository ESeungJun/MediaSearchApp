package com.project.mediasearchapp.common.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes

data class GlideImageLoadData(
    val imageView: ImageView,
    val imageUrl: String? = "",
)