package com.nadhif.hayazee.movie.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhif.hayazee.baseview.viewmodel.BaseVmFactory
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.domain.movie.GetMovieDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel(private val getMovieDetailUseCase: GetMovieDetailUseCase) : ViewModel() {

    private val _movieDetailState = MutableStateFlow<ResponseState<Movie?>>(ResponseState.Loading())
    val movieDetailState get() = _movieDetailState.asStateFlow()

    fun getMovieDetail(movieId: String) {
        viewModelScope.launch {
            getMovieDetailUseCase(movieId).collectLatest {
                _movieDetailState.value = it
            }
        }
    }


    class Factory @Inject constructor(
        getMovieDetailUseCase: GetMovieDetailUseCase
    ) : BaseVmFactory(
        MovieDetailViewModel(getMovieDetailUseCase)
    )

}