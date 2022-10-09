package com.nadhif.hayazee.domain.movie

import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.data.movie.MovieRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import javax.inject.Inject
import javax.inject.Named

class GetNowPlayingMoviesUseCase @Inject constructor(
    @Named("remote_movie_repository")
    private val movieRepository: MovieRepository
) {

    operator fun invoke(): Flow<ResponseState<List<CarouselItem>>> {
        return flow {
            emit(ResponseState.Loading())
            delay(1500)
            try {
                when (val movies = movieRepository.getNowPlayingMovies()) {
                    is ResponseState.SuccessWithData -> {
                        val carouselItems = movies.data.map {

                            CarouselItem(imageUrl = it.backdrop_path, caption = it.title ?: it.name)
                        }
                        emit(ResponseState.SuccessWithData(carouselItems))
                    }
                    is ResponseState.Success -> {
                        emit(ResponseState.Success())
                    }
                    is ResponseState.Error -> {
                        emit(ResponseState.Error(movies.message))
                    }
                    else -> Unit
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message))
            }
        }
    }
}