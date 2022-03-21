package com.project.mediasearchapp.main.fragment.search.data.source

import com.project.mediasearchapp.network.api.response.BaseResponse
import com.project.mediasearchapp.network.api.response.ImageResponse
import com.project.mediasearchapp.network.api.response.VideoResponse
import retrofit2.Response

interface ISearchRemoteSource {
    suspend fun requestSearchImage(queryMap: Map<String, String>): Response<BaseResponse<ImageResponse>>
    suspend fun requestSearchVideo(queryMap: Map<String, String>): Response<BaseResponse<VideoResponse>>
}