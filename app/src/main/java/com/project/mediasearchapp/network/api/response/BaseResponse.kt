package com.project.mediasearchapp.network.api.response

import com.google.gson.annotations.SerializedName

open class BaseResponse<DATA> {
    val documents: List<DATA>? = null
    val meta: Meta? = null
    val errorType: String? = null
    @SerializedName("message")
    val errorMsg: String? = null
}


data class Meta(
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("pageable_count")
    val pageableCount: Int,

    @SerializedName("is_end")
    val isEnd: Boolean
)