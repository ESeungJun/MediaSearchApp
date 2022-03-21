package com.project.mediasearchapp.main.fragment.search.domain.repository

import com.project.mediasearchapp.db.entity.FavoriteDataEntity
import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData
import com.project.mediasearchapp.network.api.mapper.ResponseResultMapper

interface ISearchRepository {
    suspend fun requestSearchImage(queryMap: Map<String, String>): ResponseResultMapper
    suspend fun requestSearchVideo(queryMap: Map<String, String>): ResponseResultMapper

    suspend fun clearCacheData(keyword: String)

    suspend fun addCacheKeyword(keyword: String, currentTime: Long)
    suspend fun addCacheSearchData(keyword: String, page: Int, jsonStr: String)

    suspend fun updateCacheKeyword(keyword: String, currentTime: Long)

    suspend fun getCacheKeywordSaveTime(keyword: String): Long
    suspend fun getCacheDataWithKey(keyword: String, page: Int): String?

    suspend fun getAllFavoriteList(): List<FavoriteDataEntity>
}