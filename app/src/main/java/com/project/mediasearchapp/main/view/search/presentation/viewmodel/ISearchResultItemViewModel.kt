package com.project.mediasearchapp.main.view.search.presentation.viewmodel

import com.project.mediasearchapp.main.view.search.presentation.data.SearchViewData

interface ISearchResultItemViewModel {
    fun onClickFavorite(data: SearchViewData)
}