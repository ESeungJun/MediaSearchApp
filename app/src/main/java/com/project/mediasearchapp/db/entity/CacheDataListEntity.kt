package com.project.mediasearchapp.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "CachePageData")
data class CacheDataListEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "page")
    val page: Int,
    @ColumnInfo(name = "parentKeyword")
    val parentKeyword: String = "",
    @ColumnInfo(name = "data")
    val dataJsonStr: String = "",
    @ColumnInfo(name= "isEnd")
    val isEnd: Int = 0
)
