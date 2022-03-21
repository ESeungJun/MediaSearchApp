package com.project.mediasearchapp.common.favorite.data.repository.impl

import com.project.mediasearchapp.db.entity.FavoriteDataEntity
import com.project.mediasearchapp.common.favorite.data.source.IFavoriteLocalSource
import com.project.mediasearchapp.common.favorite.domain.repository.IFavoriteRepository
import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val localSource: IFavoriteLocalSource
) : IFavoriteRepository {

    override suspend fun addFavorite(data: ImageItemViewData, saveTime: Long): Long {
        return localSource.insertFavoriteData(data, saveTime)
    }

    override suspend fun removeFavorite(data: ImageItemViewData): Int {
        return localSource.removeFavoriteData(data)
    }

    override suspend fun getAllFavoriteList(): List<FavoriteDataEntity> {
        return localSource.getAllFavoriteList() ?: emptyList()
    }
}