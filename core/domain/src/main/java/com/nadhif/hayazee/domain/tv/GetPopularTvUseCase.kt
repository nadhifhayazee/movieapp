package com.nadhif.hayazee.domain.tv

import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.data.movie.MovieRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class GetPopularTvUseCase @Inject constructor(
    @Named("remote_tv_repository")
    private val movieRepository: MovieRepository
) {

    operator fun invoke(): Flow<ResponseState<List<Movie>>> {
        return flow {
            emit(ResponseState.Loading())
            delay(2000)
            try {
                val movies = movieRepository.getPopularMovies()
                emit(movies)

            } catch (e: Exception) {
                emit(ResponseState.Error(e.localizedMessage))
            }
        }
    }
}