package com.project.mediasearchapp.main.activity.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mediasearchapp.common.favorite.domain.usecase.FavoriteUseCase
import com.project.mediasearchapp.main.fragment.favorite.presentation.data.FavoriteEventData
import com.project.mediasearchapp.itemview.presentation.data.ImageItemViewData
import com.project.mediasearchapp.itemview.presentation.viewmodel.IImageItemViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeActViewModel @Inject constructor() : ViewModel(), IImageItemViewModel {

    @Inject
    lateinit var useCase: FavoriteUseCase

    private val _favoriteEventData by lazy { MutableLiveData<FavoriteEventData>() }
    val favoriteEventData: LiveData<FavoriteEventData>
        get() = _favoriteEventData

    private val _favoriteList by lazy { MutableLiveData<List<ImageItemViewData>>() }
    val favoriteList: LiveData<List<ImageItemViewData>>
        get() = _favoriteList

    fun getAllFavoriteList() {
        viewModelScope.launch {
            val resultList = withContext(Dispatchers.IO) {
                useCase.getFavoriteList()
            }

            _favoriteList.postValue(resultList)
        }
    }

    override fun onClickFavorite(data: ImageItemViewData?) {
        data ?: return

        viewModelScope.launch {
            if (data.isFavorite) {
                if (withContext(Dispatchers.IO) { useCase.removeFavorite(data) } ) {
                    data.isFavorite = false
                    _favoriteEventData.postValue(FavoriteEventData.RemoveFavorite(data))
                }
            } else {
                if (withContext(Dispatchers.IO) { useCase.addFavorite(data) }) {
                    data.isFavorite = true
                    _favoriteEventData.postValue(FavoriteEventData.AddFavorite(data))
                }
            }
        }
    }

}