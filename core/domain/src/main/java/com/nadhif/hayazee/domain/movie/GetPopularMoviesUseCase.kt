package com.nadhif.hayazee.domain.movie

import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.common.util.EspressoIdlingResource
import com.nadhif.hayazee.data.movie.MovieRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class GetPopularMoviesUseCase @Inject constructor(
    @Named("remote_movie_repository")
    private val movieRepository: MovieRepository
) {

    operator fun invoke(): Flow<ResponseState<List<Movie>>> {
        return flow {
            EspressoIdlingResource.increment()
            emit(ResponseState.Loading())
            try {
                val movies = movieRepository.getPopularMovies()
                emit(movies)

            } catch (e: Exception) {
                emit(ResponseState.Error(e.localizedMessage))
            }
            EspressoIdlingResource.decrement()

        }
    }
}