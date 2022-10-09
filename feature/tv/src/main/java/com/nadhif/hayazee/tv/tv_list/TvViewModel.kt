package com.nadhif.hayazee.tv.tv_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhif.hayazee.baseview.viewmodel.BaseVmFactory
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.domain.tv.GetNowPlayingTvUseCase
import com.nadhif.hayazee.domain.tv.GetPopularTvUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import javax.inject.Inject

class TvViewModel(
    private val getPopularTvUseCase: GetPopularTvUseCase,
    private val getNowPlayingTvUseCase: GetNowPlayingTvUseCase
) : ViewModel() {


    private val _popularTvState =
        MutableStateFlow<ResponseState<List<Movie>>>(ResponseState.Loading())
    val popularTvState get() = _popularTvState.asStateFlow()

    private val _nowPlayingTvState =
        MutableStateFlow<ResponseState<List<CarouselItem>>>(ResponseState.Loading())
    val nowPlayingTvState get() = _nowPlayingTvState.asStateFlow()

    fun getPopularTv() {
        viewModelScope.launch {
            getPopularTvUseCase().collectLatest {
                _popularTvState.value = it
            }
        }
    }

    fun getNowPlayingTv() {
        viewModelScope.launch {
            getNowPlayingTvUseCase().collectLatest {
                _nowPlayingTvState.value = it
            }
        }
    }



    class Factory @Inject constructor(
        getPopularTvUseCase: GetPopularTvUseCase,
        getNowPlayingTvUseCase: GetNowPlayingTvUseCase
    ) : BaseVmFactory(
        TvViewModel(
            getPopularTvUseCase, getNowPlayingTvUseCase
        )
    )
}