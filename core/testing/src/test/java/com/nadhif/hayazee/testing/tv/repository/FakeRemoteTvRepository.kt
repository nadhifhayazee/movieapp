package com.nadhif.hayazee.testing.tv.repository

import com.nadhif.hayazee.common.dummy.DataDummyHelper
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.data.movie.MovieRepository

class FakeRemoteTvRepository : MovieRepository {
    override suspend fun getPopularMovies(): ResponseState<List<Movie>> {
        return ResponseState.SuccessWithData(DataDummyHelper.getPopularTvDummies())
    }

    override suspend fun getNowPlayingMovies(): ResponseState<List<Movie>> {
        return ResponseState.SuccessWithData(DataDummyHelper.getPlayingTvDummies())

    }

    override suspend fun getMovieDetail(movieId: String): ResponseState<Movie?> {
        return ResponseState.SuccessWithData(DataDummyHelper.getDummyTv())
    }
}