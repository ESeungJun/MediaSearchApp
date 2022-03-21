package com.project.mediasearchapp.main.view.search.domain.usecase

import android.media.Image
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
                        return withContext(Dispatchers.Default) {
                            totalResultList.addAll(Gson().fromJson(cacheJsonStr, Array<SearchViewData>::class.java).toList())
                            // 페이징 api를 최신순으로 요청했으나 검색결과에 더 나중의 페이징임에도 불구하고 더 최신 데이터가 들어오는 경우가 있어서 전체소팅 진행
                            totalResultList.sort()
                            ResultData.Success(totalResultList)
                        }
                    }
                }
            }

            val queryMap = createQueryMap(it, page)
            val remoteDeferred = supervisorScope {
                Log.d("haul", "call remote")
                try {
                    val tempPageList = mutableListOf<SearchViewData>()
                    val apiDeferred = mutableListOf<Deferred<Boolean>>()

                    if (!isImageApiEnd) {
                        val imageDeferred = async {
                            val resultMapper = repository.requestSearchImage(queryMap)
                            withContext(Dispatchers.Default) {
                                isImageApiEnd = (resultMapper as? ResponseResultMapper.Success<*>)?.isEnd ?: false
                                tempPageList.addAll(checkResultMapper(resultMapper))
                            }
                        }

                        apiDeferred.add(imageDeferred)
                    }

                    if (!isVideoApiEnd) {
                        val videoDeferred = async {
                            val resultMapper = repository.requestSearchVideo(queryMap)
                            withContext(Dispatchers.Default) {
                                isVideoApiEnd = (resultMapper as? ResponseResultMapper.Success<*>)?.isEnd ?: false
                                tempPageList.addAll(checkResultMapper(resultMapper))
                            }
                        }

                        apiDeferred.add(videoDeferred)
                    }

                    apiDeferred.awaitAll()

                    repository.updateCacheKeyword(it, System.currentTimeMillis())
                    repository.addCacheSearchData(it, page, Gson().toJson(tempPageList))

                    withContext(Dispatchers.Default) {
                        totalResultList.addAll(tempPageList)
                        // 페이징 api를 최신순으로 요청했으나 검색결과에 더 나중의 페이징임에도 불구하고 더 최신 데이터가 들어오는 경우가 있어서 전체소팅 진행
                        totalResultList.sort()
                        ResultData.Success(totalResultList)
                    }
                } catch (e: Exception) {
                    Log.e("error", e.printStackTrace().toString())
                    ResultData.Failed(e.printStackTrace().toString())
                }
            }

            return remoteDeferred
        } ?: run {
            return ResultData.Failed("empty keyword")
        }
    }

    private fun checkResultMapper(resultMapper: ResponseResultMapper): List<SearchViewData> {
       return when (resultMapper) {
            is ResponseResultMapper.Success<*> -> {
                if (!resultMapper.dataList.isNullOrEmpty()) {
                    checkApiResponseType(resultMapper.dataList)
                } else {
                    emptyList()
                }
            }

            else -> {
                emptyList()
            }
        }
    }

    private fun checkApiResponseType(dataList: List<*>): MutableList<SearchViewData> {
        val tempList: MutableList<SearchViewData> = mutableListOf()
        dataList.forEach { data ->
            if (data is ImageResponse) {
                tempList.add(SearchViewData(data.thumbNailUrl ?: "", data.dateTime ?: "", false))
            } else {
                if (data is VideoResponse){
                    tempList.add(SearchViewData(data.thumbNailUrl ?: "", data.dateTime ?: "", false))
                }
            }
        }

        return tempList
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