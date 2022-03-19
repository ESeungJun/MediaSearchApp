package com.project.mediasearchapp.main.view.search.domain.di

import com.project.mediasearchapp.main.view.search.data.repository.impl.SearchRepositoryImpl
import com.project.mediasearchapp.main.view.search.data.source.ISearchLocalSource
import com.project.mediasearchapp.main.view.search.data.source.ISearchRemoteSource
import com.project.mediasearchapp.main.view.search.data.source.impl.SearchLocalSourceImpl
import com.project.mediasearchapp.main.view.search.data.source.impl.SearchRemoteSourceImpl
import com.project.mediasearchapp.main.view.search.domain.repository.ISearchRepository
import com.project.mediasearchapp.network.api.RetrofitSearchApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchModule {

    @Binds
    @ViewModelScoped
    abstract fun bindSearchRepository(repositoryImpl: SearchRepositoryImpl): ISearchRepository

    @Binds
    @ViewModelScoped
    abstract fun bindSearchLocalSource(localSourceImpl: SearchLocalSourceImpl): ISearchLocalSource

    @Binds
    @ViewModelScoped
    abstract fun bindSearchRemoteSource(remoteSourceImpl: SearchRemoteSourceImpl): ISearchRemoteSource
}