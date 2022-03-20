package com.project.mediasearchapp.main.view.search.data.model

import com.project.mediasearchapp.main.view.search.presentation.data.SearchViewData

data class SearchCacheModel(
    val cacheTime: Long,
    val cacheListJsonStr: String?
)