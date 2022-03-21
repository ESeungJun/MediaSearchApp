package com.project.mediasearchapp.common.favorite.domain.di

import com.project.mediasearchapp.common.favorite.data.repository.impl.FavoriteRepositoryImpl
import com.project.mediasearchapp.common.favorite.data.source.IFavoriteLocalSource
import com.project.mediasearchapp.common.favorite.data.source.impl.FavoriteLocalSourceImpl
import com.project.mediasearchapp.common.favorite.domain.repository.IFavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class FavoriteModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsFavoriteLocalSource(localSourceImpl: FavoriteLocalSourceImpl): IFavoriteLocalSource

    @Binds
    @ViewModelScoped
    abstract fun bindsFavoriteRepository(repositoryImpl: FavoriteRepositoryImpl): IFavoriteRepository
}