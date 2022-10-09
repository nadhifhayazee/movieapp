package com.nadhif.hayazee.domain.tv

import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.data.movie.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class GetTvDetailUseCase @Inject constructor(
    @Named("remote_tv_repository")
    private val movieRepository: MovieRepository
) {

    operator fun invoke(movieId: String): Flow<ResponseState<Movie?>> {
        return flow {
            emit(ResponseState.Loading())
            try {
                val movie = movieRepository.getMovieDetail(movieId)
                emit(movie)
            } catch (e: Exception) {
                emit(ResponseState.Error(e.localizedMessage))
            }
        }
    }
}