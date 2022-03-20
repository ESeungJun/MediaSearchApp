package com.project.mediasearchapp.main.view.search.data.source.impl

import com.project.mediasearchapp.main.view.search.data.source.ISearchRemoteSource
import com.project.mediasearchapp.network.api.RetrofitSearchApi
import com.project.mediasearchapp.network.api.response.BaseResponse
import com.project.mediasearchapp.network.api.response.ImageResponse
import com.project.mediasearchapp.network.api.response.VideoResponse
import retrofit2.Response
import javax.inject.Inject

class SearchRemoteSourceImpl @Inject constructor(
    private val api: RetrofitSearchApi
) : ISearchRemoteSource {

    override suspend fun requestSearchImage(queryMap: Map<String, String>): Response<BaseResponse<ImageResponse>> {
        return api.requestSearchImage(queryMap)
    }

    override suspend fun requestSearchVideo(queryMap: Map<String, String>): Response<BaseResponse<VideoResponse>> {
        return api.requestSearchVideo(queryMap)
    }
}