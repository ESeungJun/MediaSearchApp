package com.project.mediasearchapp.network.api.response

import com.google.gson.annotations.SerializedName

/*
{
      "author": "아이유",
      "datetime": "2019-12-19T05:52:01.000+09:00",
      "play_time": 210,
      "thumbnail": "https://search1.kakaocdn.net/argon/138x78_80_pr/6iTtuO3EwAW",
      "title": "아이유(IU) 키스신(Kiss scene) 드라마 달의연인 메이킹필름",
      "url": "http://www.youtube.com/watch?v=CwIbwJzUeDk"
    },
 */
data class VideoResponse(
    val author: String?,

    @SerializedName("datetime")
    val dateTime: String?,

    @SerializedName("play_time")
    val playTime: Int,

    @SerializedName("thumbnail")
    val thumbNailUrl: String?,

    val title: String?,

    val url: String?

)
