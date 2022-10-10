package com.nadhif.hayazee.movie.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhif.hayazee.baseview.viewmodel.BaseVmFactory
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.domain.favorite.AddMovieToFavoriteUseCase
import com.nadhif.hayazee.domain.favorite.GetFavoriteByIdUseCase
import com.nadhif.hayazee.domain.movie.GetMovieDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase,
    private val getFavoriteByIdUseCase: GetFavoriteByIdUseCase
) : ViewModel() {

    private val _movieDetailState = MutableStateFlow<ResponseState<Movie?>>(ResponseState.Loading())
    val movieDetailState get() = _movieDetailState.asStateFlow()


    private val _favoriteMovieState = MutableStateFlow<Movie?>(null)
    val favoriteMovieState get() = _favoriteMovieState.asStateFlow()

    private val _isAddToFavorite = MutableStateFlow<Boolean>(false)
    val isAddToFavorite get() = _isAddToFavorite.asStateFlow()

    fun getMovieDetail(movieId: String) {
        viewModelScope.launch {
            getMovieDetailUseCase(movieId).collectLatest {
                _movieDetailState.value = it
            }
        }
    }

    fun addMovieToFavorite(movie: Movie) {
        viewModelScope.launch {
            addMovieToFavoriteUseCase(movie).collectLatest {
                when (it) {
                    is ResponseState.SuccessWithData -> {
                        _isAddToFavorite.value = it.data
                    }
                    else -> _isAddToFavorite.value = false
                }
            }
        }
    }

    fun isFavoriteMovie(id: Int) {
        viewModelScope.launch {
            getFavoriteByIdUseCase(id).collectLatest {
                when (it) {
                    is ResponseState.SuccessWithData -> {
                        _favoriteMovieState.value = it.data
                    }
                    else -> Unit
                }
            }
        }
    }


    class Factory @Inject constructor(
        getMovieDetailUseCase: GetMovieDetailUseCase,
        addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase,
        getFavoriteByIdUseCase: GetFavoriteByIdUseCase
    ) : BaseVmFactory(
        MovieDetailViewModel(
            getMovieDetailUseCase,
            addMovieToFavoriteUseCase,
            getFavoriteByIdUseCase
        )
    )

}