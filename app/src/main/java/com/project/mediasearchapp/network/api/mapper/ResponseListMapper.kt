package com.project.mediasearchapp.network.api.mapper


sealed class ResponseResultMapper {
    data class Success<DATA>(
        val isEnd: Boolean,
        val dataList: List<DATA>?
    ) : ResponseResultMapper()

    data class Error(
        val errorMsg: String?
    ) : ResponseResultMapper()

    data class Failed(
        val failedType: String?,
        val failedMsg: String?
    ) : ResponseResultMapper()
}
