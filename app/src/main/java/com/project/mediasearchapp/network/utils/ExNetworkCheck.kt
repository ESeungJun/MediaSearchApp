package com.project.mediasearchapp.network.utils

import com.project.mediasearchapp.network.api.mapper.ResponseResultMapper
import com.project.mediasearchapp.network.api.response.BaseResponse
import retrofit2.Response

inline fun <reified D, T: Response<BaseResponse<D>>> checkResponseResultList(response: T): ResponseResultMapper {
    if (!response.isSuccessful) {
        return ResponseResultMapper.Error(response.errorBody()?.string() ?: "")
    } else {
        val body = response.body()

        if (body is BaseResponse<D>) {
            return if (!body.errorType.isNullOrEmpty() || !body.errorMsg.isNullOrEmpty()) {
                ResponseResultMapper.Failed(body.errorType, body.errorMsg)
            } else {
                ResponseResultMapper.Success(body.meta?.isEnd ?: false, body.documents)
            }
        }
    }

    return ResponseResultMapper.Error("UnknownType")
}