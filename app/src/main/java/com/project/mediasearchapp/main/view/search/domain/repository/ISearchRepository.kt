package com.project.mediasearchapp.main.view.search.domain.repository

import com.project.mediasearchapp.network.api.mapper.ResponseListMapper
import com.project.mediasearchapp.network.api.response.ImageResponse
import com.project.mediasearchapp.network.api.response.VideoResponse

interface ISearchRepository {
    suspend fun requestSearchImage(queryMap: Map<String, String>): ResponseListMapper<ImageResponse>
    suspend fun requestSearchVideo(queryMap: Map<String, String>): ResponseListMapper<VideoResponse>

    suspend fun clearCacheData(keyword: String)

    suspend fun addCacheKeyword(keyword: String, currentTime: Long)
    suspend fun addCacheSearchData(keyword: String, page: Int, jsonStr: String)

    suspend fun updateCacheKeyword(keyword: String, currentTime: Long)

    suspend fun getCacheKeywordSaveTime(keyword: String): Long
    suspend fun getCacheDataWithKey(keyword: String, page: Int): String?
}