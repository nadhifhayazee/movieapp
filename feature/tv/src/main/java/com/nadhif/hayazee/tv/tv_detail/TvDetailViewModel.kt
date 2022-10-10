package com.nadhif.hayazee.tv.tv_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhif.hayazee.baseview.viewmodel.BaseVmFactory
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.domain.favorite.AddTvToFavoriteUseCase
import com.nadhif.hayazee.domain.favorite.GetFavoriteByIdUseCase
import com.nadhif.hayazee.domain.tv.GetTvDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvDetailViewModel(
    private val getTvDetailUseCase: GetTvDetailUseCase,
    private val addTvToFavoriteUseCase: AddTvToFavoriteUseCase,
    private val getFavoriteByIdUseCase: GetFavoriteByIdUseCase
) : ViewModel() {

    private val _tvDetailState = MutableStateFlow<ResponseState<Movie?>>(ResponseState.Loading())
    val tvDetailState get() = _tvDetailState.asStateFlow()

    private val _favoriteTvState = MutableStateFlow<Movie?>(null)
    val favoriteTvState get() = _favoriteTvState.asStateFlow()

    private val _isAddToFavorite = MutableStateFlow<Boolean>(false)
    val isAddToFavorite get() = _isAddToFavorite.asStateFlow()

    fun getTvDetail(movieId: String) {
        viewModelScope.launch {
            getTvDetailUseCase(movieId).collectLatest {
                _tvDetailState.value = it
            }
        }
    }

    fun addTvToFavorite(movie: Movie) {
        viewModelScope.launch {
            addTvToFavoriteUseCase(movie).collectLatest {
                when (it) {
                    is ResponseState.SuccessWithData -> {
                        _isAddToFavorite.value = it.data
                    }
                    else -> _isAddToFavorite.value = false
                }
            }
        }
    }

    fun isFavoriteTv(id: Int) {
        viewModelScope.launch {
            getFavoriteByIdUseCase(id).collectLatest {
                when (it) {
                    is ResponseState.SuccessWithData -> {
                        _favoriteTvState.value = it.data
                    }
                    else -> Unit
                }
            }
        }
    }


    class Factory @Inject constructor(
        getTvDetailUseCase: GetTvDetailUseCase,
        addTvToFavoriteUseCase: AddTvToFavoriteUseCase,
        getFavoriteByIdUseCase: GetFavoriteByIdUseCase
    ) : BaseVmFactory(
        TvDetailViewModel(getTvDetailUseCase, addTvToFavoriteUseCase, getFavoriteByIdUseCase)
    )

}