package com.nadhif.hayazee.testing.movie.repository

import com.nadhif.hayazee.common.dummy.DataDummyHelper
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.data.movie.MovieRepository

class FakeRemoteMovieRepository : MovieRepository {
    override suspend fun getPopularMovies(): ResponseState<List<Movie>> {
        return ResponseState.SuccessWithData(DataDummyHelper.getPopularMovieDummies())
    }

    override suspend fun getNowPlayingMovies(): ResponseState<List<Movie>> {
        return ResponseState.SuccessWithData(DataDummyHelper.getPlayingMovieDummies())

    }

    override suspend fun getMovieDetail(movieId: String): ResponseState<Movie?> {
        return ResponseState.SuccessWithData(DataDummyHelper.getDummyMovie())
    }
}