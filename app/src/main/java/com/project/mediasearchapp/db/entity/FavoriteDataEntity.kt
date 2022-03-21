package com.project.mediasearchapp.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteData")
data class FavoriteDataEntity(
    @PrimaryKey
    val imageUrl: String,

    @ColumnInfo(name = "saveTime")
    val saveTime: Long,

    @ColumnInfo(name = "dateTime")
    val dateTime: String,
)
