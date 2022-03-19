package com.project.mediasearchapp.main.view.search.domain.usecase

import com.project.mediasearchapp.main.view.search.domain.repository.ISearchRepository
import com.project.mediasearchapp.main.view.search.presentation.data.SearchViewData
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: ISearchRepository
) {

    private var page: Int = 1

    suspend fun getSearchResult(keyword: String, isMoreLoad: Boolean): List<SearchViewData> {
        page = if (isMoreLoad) page++ else 1
        val queryMap = mutableMapOf<String, String>()
        queryMap["query"] = keyword
        queryMap["page"] = "$page"
        queryMap["sort"] = "recency"
        queryMap["size"] = "10"

        val remoteList = repository.requestSearchImage(queryMap)


        val resultList = mutableListOf<SearchViewData>()
        resultList.addAll(remoteList.dataList?.map {
            SearchViewData(it.thumbNailUrl ?: "", it.dateTime ?: "", false)
        } ?: emptyList())

        resultList.sort()
        return resultList
    }

}