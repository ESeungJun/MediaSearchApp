package com.project.mediasearchapp.db.dao

import androidx.room.*
import com.project.mediasearchapp.db.entity.CacheDataListEntity
import com.project.mediasearchapp.db.entity.CacheKeywordEntity

@Dao
interface SearchCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKeyword(entity: CacheKeywordEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDataList(entity: CacheDataListEntity)

    @Update
    fun updateKeyword(entity: CacheKeywordEntity)

    @Query("DELETE FROM CacheKeyword WHERE keyword IN (SELECT parentKeyword FROM CachePageData WHERE parentKeyword == :keyword)")
    fun clearKeyword(keyword: String)

    @Query("SELECT * FROM CacheKeyword WHERE keyword == :keyword LIMIT 1")
    fun getKeyword(keyword: String): CacheKeywordEntity?

    @Query("SELECT A.data FROM CachePageData A WHERE A.parentKeyword == :keyword AND A.page == :page LIMIT 1")
    fun getDataListFromKeyword(keyword: String, page: Int): String?
}