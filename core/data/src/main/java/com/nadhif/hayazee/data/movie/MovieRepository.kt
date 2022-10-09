package com.nadhif.hayazee.data.movie

import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState

interface MovieRepository {

    suspend fun getPopularMovies(): ResponseState<List<Movie>>
    suspend fun getNowPlayingMovies(): ResponseState<List<Movie>>
    suspend fun getMovieDetail(movieId: String): ResponseState<Movie?>

}