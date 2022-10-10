package com.nadhif.hayazee.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhif.hayazee.baseview.viewmodel.BaseVmFactory
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.domain.favorite.GetFavoriteMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteMovieViewModel(private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase) :
    ViewModel() {

    private val _favoriteMoviesState =
        MutableStateFlow<ResponseState<List<Movie>>>(ResponseState.Loading())
    val favoriteMoviesState get() = _favoriteMoviesState.asStateFlow()

    fun getFavoriteMovies() {
        viewModelScope.launch {
            getFavoriteMoviesUseCase().collectLatest {
                _favoriteMoviesState.value = it
            }
        }
    }

    class Factory @Inject constructor(
        getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
    ) : BaseVmFactory(FavoriteMovieViewModel(getFavoriteMoviesUseCase))
}