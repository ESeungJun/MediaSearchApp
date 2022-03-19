package com.project.mediasearchapp.network.api

import com.project.mediasearchapp.network.api.response.BaseResponse
import com.project.mediasearchapp.network.api.response.ImageResponse
import com.project.mediasearchapp.network.api.response.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RetrofitSearchApi {

    @GET("v2/search/image")
    suspend fun requestSearchImage(@QueryMap queryMap: Map<String, String>): Response<BaseResponse<ImageResponse>>

    @GET("v2/search/vclip")
    suspend fun requestSearchVideo(@QueryMap queryMap: Map<String, String>): Response<BaseResponse<VideoResponse>>
}