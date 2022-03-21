package com.project.mediasearchapp.common.favorite.data.source.impl

import com.project.mediasearchapp.db.dao.FavoriteDao
import com.project.mediasearchapp.db.entity.FavoriteDataEntity
import com.project.mediasearchapp.common.favorite.data.source.IFavoriteLocalSource
import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData
import javax.inject.Inject

class FavoriteLocalSourceImpl @Inject constructor(): IFavoriteLocalSource {

    @Inject
    lateinit var favoriteDao: FavoriteDao

    override suspend fun insertFavoriteData(data: ImageItemViewData, saveTime: Long) : Long {
        return favoriteDao.insertFavoriteData(FavoriteDataEntity(data.imageUrl, saveTime, data.dateTime))
    }

    override suspend fun removeFavoriteData(data: ImageItemViewData): Int {
        return favoriteDao.removeFavoriteData(FavoriteDataEntity(data.imageUrl, 0, data.dateTime))
    }

    override suspend fun getAllFavoriteList(): List<FavoriteDataEntity>? {
        return favoriteDao.getAllFavoriteData()
    }
}