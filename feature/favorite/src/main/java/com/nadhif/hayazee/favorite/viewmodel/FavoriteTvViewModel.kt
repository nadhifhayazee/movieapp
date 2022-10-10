package com.nadhif.hayazee.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhif.hayazee.baseview.viewmodel.BaseVmFactory
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.domain.favorite.GetFavoriteTvUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteTvViewModel(private val getFavoriteTvUseCase: GetFavoriteTvUseCase) :
    ViewModel() {

    private val _favoriteTvState =
        MutableStateFlow<ResponseState<List<Movie>>>(ResponseState.Loading())
    val favoriteTvsState get() = _favoriteTvState.asStateFlow()

    fun getFavoriteTvs() {
        viewModelScope.launch {
            getFavoriteTvUseCase().collectLatest {
                _favoriteTvState.value = it
            }
        }
    }

    class Factory @Inject constructor(
        getFavoriteTvUseCase: GetFavoriteTvUseCase
    ) : BaseVmFactory(FavoriteTvViewModel(getFavoriteTvUseCase))
}