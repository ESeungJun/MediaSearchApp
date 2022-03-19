package com.project.mediasearchapp.main.view.search.data.repository.impl

import com.project.mediasearchapp.main.view.search.data.source.ISearchLocalSource
import com.project.mediasearchapp.main.view.search.data.source.ISearchRemoteSource
import com.project.mediasearchapp.main.view.search.domain.repository.ISearchRepository
import com.project.mediasearchapp.network.api.mapper.ResponseListMapper
import com.project.mediasearchapp.network.api.response.ImageResponse
import com.project.mediasearchapp.network.utils.checkResponseResultList
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteSource: ISearchRemoteSource,
    private val localSource: ISearchLocalSource
) : ISearchRepository {

    override suspend fun requestSearchImage(queryMap: Map<String, String>): ResponseListMapper<ImageResponse> {
        return checkResponseResultList(remoteSource.requestSearchImage(queryMap))
    }
}