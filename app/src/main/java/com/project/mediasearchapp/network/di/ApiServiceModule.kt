package com.project.mediasearchapp.network.di

import com.project.mediasearchapp.network.api.RetrofitSearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    fun provideSearchApi(retrofit: Retrofit): RetrofitSearchApi {
        return retrofit.create(RetrofitSearchApi::class.java)
    }
}