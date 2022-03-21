package com.project.mediasearchapp.db.di

import android.content.Context
import androidx.room.Room
import com.project.mediasearchapp.db.MediaSearchDataBase
import com.project.mediasearchapp.db.dao.FavoriteDao
import com.project.mediasearchapp.db.dao.SearchCacheDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideMediaSearchDataBase(@ApplicationContext context: Context): MediaSearchDataBase {
        return MediaSearchDataBase.getInstance(context)
    }

    @Provides
    fun provideSearchCacheDao(dataBase: MediaSearchDataBase): SearchCacheDao = dataBase.searchCacheDao()

    @Provides
    fun provideFavoriteDao(dataBase: MediaSearchDataBase): FavoriteDao = dataBase.favoriteDao()
}