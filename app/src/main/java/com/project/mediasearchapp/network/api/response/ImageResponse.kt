package com.project.mediasearchapp.network.api.response

import com.google.gson.annotations.SerializedName

/*
{
      "collection": "cafe",
      "datetime": "2020-05-04T21:25:49.000+09:00",
      "display_sitename": "Daum카페",
      "doc_url": "http://cafe.daum.net/csy95201155/GGfj/48022",
      "height": 830,
      "image_url": "https://t1.daumcdn.net/news/201510/05/ned/20151005103605335qjsj.jpg",
      "thumbnail_url": "https://search3.kakaocdn.net/argon/130x130_85_c/HOgtg2v9HrQ",
      "width": 540
    },
 */
data class ImageResponse(
    val collection: String?,

    @SerializedName("datetime")
    val dateTime: String?,

    @SerializedName("display_sitename")
    val displaySiteName: String?,

    @SerializedName("doc_url")
    val docUrl: String?,

    val height: Int,

    @SerializedName("image_url")
    val imageUrl: String?,

    @SerializedName("thumbnail_url")
    val thumbNailUrl: String?,

    val width: Int
)
