package com.project.mediasearchapp.main.fragment.favorite.presentation.data

import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData

sealed class FavoriteEventData {
    data class AddFavorite(val data: ImageItemViewData) : FavoriteEventData()
    data class RemoveFavorite(val data: ImageItemViewData) : FavoriteEventData()
}
