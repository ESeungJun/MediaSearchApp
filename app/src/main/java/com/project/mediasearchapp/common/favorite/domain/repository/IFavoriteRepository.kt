package com.project.mediasearchapp.common.favorite.domain.repository

import com.project.mediasearchapp.db.entity.FavoriteDataEntity
import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData

interface IFavoriteRepository {
    suspend fun addFavorite(data: ImageItemViewData, saveTime: Long): Long
    suspend fun removeFavorite(data: ImageItemViewData): Int
    suspend fun getAllFavoriteList(): List<FavoriteDataEntity>
}