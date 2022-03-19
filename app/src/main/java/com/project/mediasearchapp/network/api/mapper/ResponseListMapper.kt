package com.project.mediasearchapp.network.api.mapper


data class ResponseListMapper<DATA>(
    val isSuccess: Boolean,
    val dataList: List<DATA>?,
    val errorMessage: String?
)
