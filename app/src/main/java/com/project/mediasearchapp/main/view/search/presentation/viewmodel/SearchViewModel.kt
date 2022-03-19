package com.project.mediasearchapp.main.view.search.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mediasearchapp.main.view.search.domain.usecase.SearchUseCase
import com.project.mediasearchapp.main.view.search.presentation.data.SearchViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel(), ISearchResultItemViewModel {

    @Inject
    lateinit var useCase: SearchUseCase

    private val _searchResultList by lazy { MutableLiveData<List<SearchViewData>>() }
    val searchResultList: LiveData<List<SearchViewData>>
        get() = _searchResultList


    fun getSearchResult(keyword: String, isMoreLoad: Boolean) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                useCase.getSearchResult(keyword, isMoreLoad)
            }

            _searchResultList.postValue(result)
        }
    }


    override fun onClickFavorite(data: SearchViewData) {

    }
}