package com.nadhif.hayazee.data.module

import com.nadhif.hayazee.data.movie.MovieRepository
import com.nadhif.hayazee.data.movie.RemoteMovieRepositoryImpl
import com.nadhif.hayazee.data.tv.RemoteTvRepositoryImpl
import com.nadhif.hayazee.network.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    @Singleton
    @Named("remote_movie_repository")
    fun provideRemoteMovieRepository(
        apiService: ApiService
    ): MovieRepository {
        return RemoteMovieRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    @Named("remote_tv_repository")
    fun provideRemoteTvRepository(
        apiService: ApiService
    ): MovieRepository {
        return RemoteTvRepositoryImpl(apiService)
    }
}