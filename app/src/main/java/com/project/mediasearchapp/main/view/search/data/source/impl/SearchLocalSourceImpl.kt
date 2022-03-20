package com.project.mediasearchapp.main.view.search.data.source.impl

import com.project.mediasearchapp.db.dao.SearchCacheDao
import com.project.mediasearchapp.db.entity.*
import com.project.mediasearchapp.main.view.search.data.source.ISearchLocalSource
import javax.inject.Inject

class SearchLocalSourceImpl @Inject constructor(): ISearchLocalSource {

    @Inject
    lateinit var searchCacheDao: SearchCacheDao

    override suspend fun insertCacheKeyword(keyword: String, currentTime: Long) {
        searchCacheDao.insertKeyword(CacheKeywordEntity(keyword, currentTime))
    }

    override suspend fun insertCacheData(page: Int, keyword: String, jsonStr: String) {
        searchCacheDao.insertDataList(CacheDataListEntity("${page}_${keyword}", page, keyword, jsonStr))
    }

    override suspend fun clearCacheData(keyword: String) {
        searchCacheDao.clearKeyword(keyword)
    }

    override suspend fun updateCacheKeyword(keyword: String, currentTime: Long) {
        searchCacheDao.updateKeyword(CacheKeywordEntity(keyword, currentTime))
    }

    override suspend fun getCacheKeyword(keyword: String): CacheKeywordEntity? {
        return searchCacheDao.getKeyword(keyword)
    }

    override suspend fun getCacheData(page: Int, keyword: String): String? {
        return searchCacheDao.getDataListFromKeyword(keyword, page)
    }
}