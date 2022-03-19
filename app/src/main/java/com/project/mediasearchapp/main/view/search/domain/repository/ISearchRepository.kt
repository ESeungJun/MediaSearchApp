package com.project.mediasearchapp.main.view.search.domain.repository

import com.project.mediasearchapp.network.api.mapper.ResponseListMapper
import com.project.mediasearchapp.network.api.response.ImageResponse
import retrofit2.Response

interface ISearchRepository {
    suspend fun requestSearchImage(queryMap: Map<String, String>): ResponseListMapper<ImageResponse>
}