package com.project.mediasearchapp.main.view.search.domain.usecase

import android.util.Log
import com.google.gson.Gson
import com.project.mediasearchapp.main.view.search.domain.repository.ISearchRepository
import com.project.mediasearchapp.main.view.search.presentation.data.SearchViewData
import com.project.mediasearchapp.network.api.mapper.ResponseResultMapper
import com.project.mediasearchapp.network.api.response.BaseResponse
import com.project.mediasearchapp.network.api.response.ImageResponse
import com.project.mediasearchapp.network.api.response.VideoResponse
import kotlinx.coroutines.*
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: ISearchRepository
) {

    private var preKeyword: String? = null
    private val totalResultList = mutableListOf<SearchViewData>()

    private var isImageApiEnd = false
    private var isVideoApiEnd = false

    suspend fun getSearchResult(keyword: String?, isMoreLoad: Boolean = false, page: Int = 1): ResultData {
        if (!isMoreLoad) {
            preKeyword = null
            preKeyword = keyword

            isImageApiEnd = false
            isVideoApiEnd = false

            totalResultList.clear()
        }

        if (isImageApiEnd && isVideoApiEnd) {
            return ResultData.Failed("End Data")
        }

        preKeyword?.let { it ->

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
                        return ResultData.Success(totalResultList)
                    }
                }
            }

            val queryMap = createQueryMap(it, page)
            val remoteDeferred = supervisorScope {
                try {
                    val apiDeferred = mutableListOf<Deferred<ResponseResultMapper>>()
                    if (!isImageApiEnd) {
                        apiDeferred.add(async { repository.requestSearchImage(queryMap) })
                    }

                    if (!isVideoApiEnd) {
                        apiDeferred.add(async { repository.requestSearchVideo(queryMap) })
                    }

                    apiDeferred.toList().awaitAll()
                } catch (e: Exception) {
                    Log.e("error", e.printStackTrace().toString())
                    emptyList()
                }
            }

            val result = withContext(Dispatchers.Default) {
                val tempList = mutableListOf<SearchViewData>()
                val successMapper = remoteDeferred.filterIsInstance<ResponseResultMapper.Success<*>>()
                if (successMapper.isNotEmpty()) {
                    successMapper.forEach { sm ->
                        tempList.addAll(sm.dataList?.map { data ->
                            when (data) {
                                is ImageResponse -> {
                                    isImageApiEnd = sm.isEnd
                                    SearchViewData(data.thumbNailUrl ?: "", data.dateTime ?: "", false)
                                }

                                is VideoResponse -> {
                                    isVideoApiEnd = sm.isEnd
                                    SearchViewData(data.thumbNailUrl ?: "", data.dateTime ?: "", false)
                                }

                                else -> {
                                    SearchViewData("", "", false)
                                }
                            }
                        } ?: emptyList())


                        repository.updateCacheKeyword(it, System.currentTimeMillis())
                        repository.addCacheSearchData(it, page, Gson().toJson(tempList))

                        totalResultList.addAll(tempList)
                        totalResultList.sort()
                    }

                    ResultData.Success(totalResultList)
                } else {
                    ResultData.Failed("Not Success All Api")
                }
            }

            return result
        } ?: run {
            return ResultData.Failed("empty keyword")
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

    sealed class ResultData {
        data class Success(val dataList: List<SearchViewData>) : ResultData()
        data class Failed(val msg: String, val type: String? = null) : ResultData()
    }
}