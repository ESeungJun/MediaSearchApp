package com.project.mediasearchapp.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CacheKeyword")
data class CacheKeywordEntity(
    @PrimaryKey
    val keyword: String,
    @ColumnInfo(name = "last_cache_time")
    val lastCacheTime: Long,
)