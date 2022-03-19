package com.project.mediasearchapp.network.utils

import com.project.mediasearchapp.network.api.mapper.ResponseListMapper
import com.project.mediasearchapp.network.api.response.BaseResponse
import retrofit2.Response

inline fun <reified D, T: Response<BaseResponse<D>>> checkResponseResultList(response: T): ResponseListMapper<D> {

    val isSuccess = response.isSuccessful
    var errorString: String? = null
    var data: List<D>? = null

    if (!isSuccess) {
        errorString = response.errorBody()?.string() ?: ""
    } else {
        val body = response.body()

        if (body is BaseResponse<D>) {
            if (!body.errorType.isNullOrEmpty() || !body.errorMsg.isNullOrEmpty()) {
                errorString = body.errorMsg
                data = null
            } else {
                data = body.documents
            }
        }
    }

    return ResponseListMapper(isSuccess, data, errorString)
}