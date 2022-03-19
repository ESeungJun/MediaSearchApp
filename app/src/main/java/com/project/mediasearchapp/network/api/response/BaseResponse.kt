package com.project.mediasearchapp.network.api.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<DATA>(
    val documents: List<DATA>?,
    val errorType: String?,
    @SerializedName("message")
    val errorMsg: String?
)