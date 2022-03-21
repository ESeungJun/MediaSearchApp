package com.project.mediasearchapp.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.project.mediasearchapp.db.entity.FavoriteDataEntity

@Dao
interface FavoriteDao {

    @Insert
    fun insertFavoriteData(entity: FavoriteDataEntity): Long

    @Delete
    fun removeFavoriteData(entity: FavoriteDataEntity): Int

    @Query("SELECT * FROM FavoriteData ORDER BY saveTime DESC ")
    fun getAllFavoriteData(): List<FavoriteDataEntity>?
}