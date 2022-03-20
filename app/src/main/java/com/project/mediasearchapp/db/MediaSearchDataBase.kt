package com.project.mediasearchapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.mediasearchapp.db.dao.SearchCacheDao
import com.project.mediasearchapp.db.entity.CacheDataListEntity
import com.project.mediasearchapp.db.entity.CacheKeywordEntity

@Database(entities = [CacheKeywordEntity::class, CacheDataListEntity::class], version = 1, exportSchema = false)
abstract class MediaSearchDataBase : RoomDatabase() {
    abstract fun searchCacheDao(): SearchCacheDao

    companion object {
        @Volatile
        private var INSTANCE: MediaSearchDataBase? = null

        fun getInstance(context: Context): MediaSearchDataBase {
            synchronized(this) {
                var instance = INSTANCE

                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MediaSearchDataBase::class.java,
                        "media_search"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}