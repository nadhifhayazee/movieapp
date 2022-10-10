package com.nadhif.hayazee.data.module

import com.nadhif.hayazee.data.favorite.FavoriteRepository
import com.nadhif.hayazee.data.favorite.LocalFavoriteRepositoryImpl
import com.nadhif.hayazee.database.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FavoriteModule {

    @Provides
    fun provideLocalFavoriteRepository(
        favoriteDao: FavoriteDao
    ): FavoriteRepository {
        return LocalFavoriteRepositoryImpl(favoriteDao)
    }
}