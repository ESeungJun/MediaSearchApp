package com.project.mediasearchapp.main.fragment.search.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mediasearchapp.main.fragment.search.domain.usecase.SearchUseCase
import com.project.mediasearchapp.main.fragment.search.presentation.data.SearchActionEvent
import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var useCase: SearchUseCase

    private val _errorMsg by lazy { MutableLiveData<String>() }
    val errorMsg: LiveData<String>
        get() = _errorMsg

    private val _searchResultList by lazy { MutableLiveData<List<ImageItemViewData>>() }
    val imageItemResultList: LiveData<List<ImageItemViewData>>
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

            when (result) {
                is SearchUseCase.ResultData.Success -> {
                    _searchResultList.postValue(result.dataList)
                }

                else -> {
                    _errorMsg.postValue((result as? SearchUseCase.ResultData.Failed)?.msg)
                }
            }
        }
    }

    fun moreLoadSearchResult(page: Int) {
        getSearchResult(null, true, page)
    }


    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }
}