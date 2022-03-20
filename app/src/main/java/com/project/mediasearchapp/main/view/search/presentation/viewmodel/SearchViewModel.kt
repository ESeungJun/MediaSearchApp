package com.project.mediasearchapp.main.view.search.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mediasearchapp.main.view.search.domain.usecase.SearchUseCase
import com.project.mediasearchapp.main.view.search.presentation.data.SearchActionEvent
import com.project.mediasearchapp.main.view.search.presentation.data.SearchViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel(), ISearchResultItemViewModel {

    @Inject
    lateinit var useCase: SearchUseCase

    private val _searchResultList by lazy { MutableLiveData<List<SearchViewData>>() }
    val searchResultList: LiveData<List<SearchViewData>>
        get() = _searchResultList

    private val _searchActionEvent by lazy { MutableLiveData<SearchActionEvent>() }
    val searchActionEvent: LiveData<SearchActionEvent>
        get() = _searchActionEvent

    private var searchJob: Job? = null

    fun getSearchResult(keyword: String?, isMoreLoad: Boolean, page: Int) {
        if (!isMoreLoad) {
            searchJob?.cancel()
            _searchActionEvent.postValue(SearchActionEvent.OnInitSearchEvent)
        }

        searchJob = viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                useCase.getSearchResult(keyword, isMoreLoad, page)
            }

            _searchResultList.postValue(result)
        }
    }

    fun moreLoadSearchResult(page: Int) {
        getSearchResult(null, true, page)
    }


    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }

    override fun onClickFavorite(data: SearchViewData) {

    }
}