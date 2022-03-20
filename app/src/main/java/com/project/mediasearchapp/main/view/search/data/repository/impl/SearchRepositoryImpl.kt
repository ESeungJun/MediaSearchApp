package com.project.mediasearchapp.main.view.search.data.repository.impl

import com.project.mediasearchapp.main.view.search.data.source.ISearchLocalSource
import com.project.mediasearchapp.main.view.search.data.source.ISearchRemoteSource
import com.project.mediasearchapp.main.view.search.domain.repository.ISearchRepository
import com.project.mediasearchapp.network.api.mapper.ResponseListMapper
import com.project.mediasearchapp.network.api.response.ImageResponse
import com.project.mediasearchapp.network.api.response.VideoResponse
import com.project.mediasearchapp.network.utils.checkResponseResultList
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteSource: ISearchRemoteSource,
    private val localSource: ISearchLocalSource
) : ISearchRepository {

    override suspend fun requestSearchImage(queryMap: Map<String, String>): ResponseListMapper<ImageResponse> {
        return checkResponseResultList(remoteSource.requestSearchImage(queryMap))
    }

    override suspend fun requestSearchVideo(queryMap: Map<String, String>): ResponseListMapper<VideoResponse> {
        return checkResponseResultList(remoteSource.requestSearchVideo(queryMap))
    }

    override suspend fun clearCacheData(keyword: String) {
        localSource.clearCacheData(keyword)
    }

    override suspend fun addCacheKeyword(keyword: String, currentTime: Long) {
        localSource.insertCacheKeyword(keyword, currentTime)
    }

    override suspend fun addCacheSearchData(keyword: String, page: Int, jsonStr: String) {
        localSource.insertCacheData(page, keyword, jsonStr)
    }

    override suspend fun updateCacheKeyword(keyword: String, currentTime: Long) {
        localSource.updateCacheKeyword(keyword, currentTime)
    }

    override suspend fun getCacheKeywordSaveTime(keyword: String): Long {
        return localSource.getCacheKeyword(keyword)?.lastCacheTime ?: -1L
    }

    override suspend fun getCacheDataWithKey(keyword: String, page: Int): String? {
        return localSource.getCacheData(page, keyword)
    }
}