package com.nadhif.hayazee.tv.tv_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhif.hayazee.baseview.viewmodel.BaseVmFactory
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.domain.tv.GetTvDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvDetailViewModel(private val getTvDetailUseCase: GetTvDetailUseCase) : ViewModel() {

    private val _tvDetailState = MutableStateFlow<ResponseState<Movie?>>(ResponseState.Loading())
    val tvDetailState get() = _tvDetailState.asStateFlow()

    fun getTvDetail(movieId: String) {
        viewModelScope.launch {
            getTvDetailUseCase(movieId).collectLatest {
                _tvDetailState.value = it
            }
        }
    }


    class Factory @Inject constructor(
        getTvDetailUseCase: GetTvDetailUseCase
    ) : BaseVmFactory(
        TvDetailViewModel(getTvDetailUseCase)
    )

}