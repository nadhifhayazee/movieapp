package com.nadhif.hayazee.data.movie

import com.nadhif.hayazee.common.BuildConfig
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.network.service.ApiService
import javax.inject.Inject

class RemoteMovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MovieRepository {
    override suspend fun getPopularMovies(): ResponseState<List<Movie>> {
        try {
            val response = apiService.getPopularMovies(
                BuildConfig.API_KEY
            )
            return if (response.isSuccessful) {
                if (response.body()?.results.isNullOrEmpty()) {
                    ResponseState.Success()
                } else {
                    ResponseState.SuccessWithData(response.body()?.results ?: arrayListOf())
                }
            } else {
                ResponseState.Error(response.message())
            }
        } catch (e: Exception) {
            return ResponseState.Error(e.localizedMessage)
        }

    }

    override suspend fun getNowPlayingMovies(): ResponseState<List<Movie>> {
        try {
            val response = apiService.getNowPlayingMovies(
                BuildConfig.API_KEY
            )
            return if (response.isSuccessful) {
                if (response.body()?.results.isNullOrEmpty()) {
                    ResponseState.Success()
                } else {
                    ResponseState.SuccessWithData(response.body()?.results ?: arrayListOf())
                }
            } else {
                ResponseState.Error(response.message())
            }
        } catch (e: Exception) {
            return ResponseState.Error(e.localizedMessage)
        }
    }

    override suspend fun getMovieDetail(movieId: String): ResponseState<Movie?> {
        try {
            val response = apiService.getDetailMovie(
                movieId,
                BuildConfig.API_KEY
            )
            return if (response.isSuccessful) {
                if (response.body() == null) {
                    ResponseState.Success()
                } else {
                    ResponseState.SuccessWithData(response.body())
                }
            } else {
                ResponseState.Error(response.message())
            }
        } catch (e: Exception) {
            return ResponseState.Error(e.localizedMessage)
        }
    }
}