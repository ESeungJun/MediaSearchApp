package com.project.mediasearchapp.common.favorite.data.source

import com.project.mediasearchapp.db.entity.FavoriteDataEntity
import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData

interface IFavoriteLocalSource {
    suspend fun insertFavoriteData(data: ImageItemViewData, saveTime: Long): Long
    suspend fun removeFavoriteData(data: ImageItemViewData): Int
    suspend fun getAllFavoriteList(): List<FavoriteDataEntity>?
}