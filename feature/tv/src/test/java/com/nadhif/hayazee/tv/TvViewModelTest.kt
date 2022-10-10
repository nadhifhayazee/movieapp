package com.nadhif.hayazee.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nadhif.hayazee.common.dummy.DataDummyHelper
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.domain.tv.GetNowPlayingTvUseCase
import com.nadhif.hayazee.domain.tv.GetPopularTvUseCase
import com.nadhif.hayazee.tv.tv_list.TvViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TvViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var tvViewModel: TvViewModel

    @Mock
    private lateinit var getPopularTvUseCase: GetPopularTvUseCase

    @Mock
    private lateinit var getNowPlayingTvUseCase: GetNowPlayingTvUseCase

    @Before
    fun setup() {
        tvViewModel = TvViewModel(
            getPopularTvUseCase, getNowPlayingTvUseCase
        )

    }

    @Test
    fun `get popular tv`() = runTest {
        val popularTvDummy = DataDummyHelper.getPopularTvDummies()

        `when`(getPopularTvUseCase()).then {
            flow {
                emit(ResponseState.SuccessWithData(popularTvDummy))
            }
        }

        val states = mutableListOf<ResponseState<List<Movie>>>()
        val job = launch(UnconfinedTestDispatcher()) {
            tvViewModel.popularTvState.toList(states)
        }

        tvViewModel.getPopularTv()

        val firstState = states[0] is ResponseState.Loading
        val secondState = states[1] is ResponseState.SuccessWithData


        assertEquals(true, firstState)
        assertEquals(true, secondState)
        assertNotNull((states[1] as ResponseState.SuccessWithData).data)
        assertEquals(5, (states[1] as ResponseState.SuccessWithData).data.size)

        job.cancel()
    }

    @Test
    fun `get now playing tv`() = runTest {
        val nowPlayingTvDummy = DataDummyHelper.getPlayingTvDummies()

        `when`(getNowPlayingTvUseCase()).then {
            flow {
                val carouselItems = nowPlayingTvDummy.map {
                    CarouselItem(
                        imageUrl = it.backdrop_path,
                        caption = it.title ?: it.name
                    )
                }
                emit(ResponseState.SuccessWithData(carouselItems))
            }
        }

        val states = mutableListOf<ResponseState<List<CarouselItem>>>()
        val job = launch(UnconfinedTestDispatcher()) {
            tvViewModel.nowPlayingTvState.toList(states)
        }

        tvViewModel.getNowPlayingTv()

        val firstState = states[0] is ResponseState.Loading
        val secondState = states[1] is ResponseState.SuccessWithData


        assertEquals(true, firstState)
        assertEquals(true, secondState)
        assertNotNull((states[1] as ResponseState.SuccessWithData).data)
        assertEquals(8, (states[1] as ResponseState.SuccessWithData).data.size)

        job.cancel()
    }
}