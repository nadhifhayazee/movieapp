package com.nadhif.hayazee.movie.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhif.hayazee.baseview.viewmodel.BaseVmFactory
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.domain.movie.GetNowPlayingMoviesUseCase
import com.nadhif.hayazee.domain.movie.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import javax.inject.Inject

class MovieViewModel(
    private val getPopularMovieUseCase: GetPopularMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {

    private val _popularMoviesState =
        MutableStateFlow<ResponseState<List<Movie>>>(ResponseState.Loading())
    val popularMoviesState get() = _popularMoviesState.asStateFlow()

    private val _nowPlayingMoviesState =
        MutableStateFlow<ResponseState<List<CarouselItem>>>(ResponseState.Loading())
    val nowPlayingMovieState get() = _nowPlayingMoviesState.asStateFlow()

    fun getPopularMovies() {
        viewModelScope.launch {
            getPopularMovieUseCase().collectLatest {
                _popularMoviesState.value = it
            }
        }
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            getNowPlayingMoviesUseCase().collectLatest {
                _nowPlayingMoviesState.value = it
            }
        }
    }


    class Factory @Inject constructor(
        getPopularMovieUseCase: GetPopularMoviesUseCase,
        getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
    ) : BaseVmFactory(
        MovieViewModel(getPopularMovieUseCase, getNowPlayingMoviesUseCase)
    )


}