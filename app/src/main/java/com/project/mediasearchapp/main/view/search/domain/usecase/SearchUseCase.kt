package com.project.mediasearchapp.main.view.search.domain.usecase

import android.util.Log
import com.google.gson.Gson
import com.project.mediasearchapp.main.view.search.domain.repository.ISearchRepository
import com.project.mediasearchapp.main.view.search.presentation.data.SearchViewData
import com.project.mediasearchapp.network.api.response.ImageResponse
import com.project.mediasearchapp.network.api.response.VideoResponse
import kotlinx.coroutines.*
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: ISearchRepository
) {

    private var preKeyword: String? = null
    private val totalResultList = mutableListOf<SearchViewData>()

    suspend fun getSearchResult(keyword: String?, isMoreLoad: Boolean = false, page: Int = 1): List<SearchViewData> {
        if (!isMoreLoad) {
            preKeyword = null
            preKeyword = keyword

            totalResultList.clear()
        }

        preKeyword?.let {

            val lastCacheKeywordTime = repository.getCacheKeywordSaveTime(it)

            if (lastCacheKeywordTime <= 0) {
                repository.addCacheKeyword(it, System.currentTimeMillis())
            } else {
                if (System.currentTimeMillis() - lastCacheKeywordTime >= CACHE_CLEAR_TIME) {
                    repository.clearCacheData(it)
                } else {
                    val cacheJsonStr = repository.getCacheDataWithKey(it, page)

                    if (!cacheJsonStr.isNullOrEmpty()) {
                        totalResultList.addAll(Gson().fromJson(cacheJsonStr, Array<SearchViewData>::class.java).toList())
                        return totalResultList
                    }
                }
            }

            val queryMap = createQueryMap(it, page)
            val remoteDeferred = supervisorScope {
                try {
                    val imageDeferred = async { repository.requestSearchImage(queryMap) }
                    val videoDeferred = async { repository.requestSearchVideo(queryMap) }
                    awaitAll(imageDeferred, videoDeferred)
                } catch (e: Exception) {
                    Log.e("error", e.printStackTrace().toString())
                    emptyList()
                }
            }

            val sortDeferred = withContext(Dispatchers.Default) {
                val tempList = mutableListOf<SearchViewData>()
                async {
                    remoteDeferred.forEach { mapper ->
                        if (mapper.isSuccess) {
                            tempList.addAll(mapper.dataList?.map { data ->
                                when (data) {
                                    is ImageResponse -> {
                                        SearchViewData(data.thumbNailUrl ?: "", data.dateTime ?: "", false)
                                    }

                                    is VideoResponse -> {
                                        SearchViewData(data.thumbNailUrl ?: "", data.dateTime ?: "", false)
                                    }

                                    else -> {
                                        SearchViewData("", "", false)
                                    }
                                }
                            } ?: emptyList())
                        }
                    }

                    withContext(Dispatchers.IO) {
                        repository.updateCacheKeyword(it, System.currentTimeMillis())
                        repository.addCacheSearchData(it, page, Gson().toJson(tempList))
                    }

                    totalResultList.addAll(tempList)
                    totalResultList.sort()
                }
            }

            return withContext(Dispatchers.Main) {
                sortDeferred.await()
                totalResultList
            }
        } ?: run {
            return emptyList()
        }
    }

    private fun createQueryMap(keyword: String, page: Int): Map<String, String> {
        val queryMap = mutableMapOf<String, String>()
        queryMap["query"] = keyword
        queryMap["page"] = "$page"
        queryMap["sort"] = "recency"
        queryMap["size"] = "10"

        return queryMap
    }


    companion object {
        private const val CACHE_CLEAR_TIME = 5 * 60 * 1000L
    }
}