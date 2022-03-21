package com.project.mediasearchapp.common.favorite.domain.usecase

import com.project.mediasearchapp.common.favorite.domain.repository.IFavoriteRepository
import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData
import com.project.mediasearchapp.utils.GsonUtils
import com.project.mediasearchapp.utils.SharedPreferencesUtils
import com.project.mediasearchapp.utils.SharedPreferencesUtils.Companion.KEY_FAVORITE_LIST
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(
    private val repository: IFavoriteRepository
) {

    suspend fun addFavorite(data: ImageItemViewData): Boolean {
        val addResult = repository.addFavorite(data, System.currentTimeMillis()) > 0
        if (addResult) {
            withContext(Dispatchers.Default) {
                val favJson = SharedPreferencesUtils.getInstance().getString(KEY_FAVORITE_LIST, "")
                val favList = GsonUtils.convertJsonToList(favJson, ImageItemViewData::class.java) as MutableList<ImageItemViewData>
                favList.add(data)
                SharedPreferencesUtils.getInstance().setList(KEY_FAVORITE_LIST, favList)
            }
        }
        return addResult
    }

    suspend fun removeFavorite(data: ImageItemViewData): Boolean {
        val removeResult = repository.removeFavorite(data) > 0
        if (removeResult) {
            withContext(Dispatchers.Default) {
                val favJson = SharedPreferencesUtils.getInstance().getString(KEY_FAVORITE_LIST, "")
                val favList = GsonUtils.convertJsonToList(favJson, ImageItemViewData::class.java) as MutableList<ImageItemViewData>
                favList.remove(data)
                SharedPreferencesUtils.getInstance().setList(KEY_FAVORITE_LIST, favList)
            }
        }
        return removeResult
    }

    suspend fun getFavoriteList(): List<ImageItemViewData> {
        val favList = repository.getAllFavoriteList().map { ImageItemViewData(it.imageUrl, it.dateTime, true) }
        SharedPreferencesUtils.getInstance().setList(KEY_FAVORITE_LIST, favList)
        return favList
    }
}