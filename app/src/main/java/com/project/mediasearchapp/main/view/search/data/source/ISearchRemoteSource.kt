package com.project.mediasearchapp.main.view.search.data.source

import com.project.mediasearchapp.network.api.response.BaseResponse
import com.project.mediasearchapp.network.api.response.ImageResponse
import retrofit2.Response

interface ISearchRemoteSource {
    suspend fun requestSearchImage(queryMap: Map<String, String>): Response<BaseResponse<ImageResponse>>
}