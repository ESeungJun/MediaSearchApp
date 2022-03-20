package com.project.mediasearchapp.main.view.search.data.source

import com.project.mediasearchapp.db.entity.CacheKeywordEntity

interface ISearchLocalSource {
    suspend fun insertCacheKeyword(keyword: String, currentTime: Long)
    suspend fun insertCacheData(page: Int, keyword: String, jsonStr: String)

    suspend fun clearCacheData(keyword: String)

    suspend fun updateCacheKeyword(keyword: String, currentTime: Long)

    suspend fun getCacheKeyword(keyword: String): CacheKeywordEntity?
    suspend fun getCacheData(page: Int, keyword: String): String?
}