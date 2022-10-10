package com.nadhif.hayazee.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nadhif.hayazee.common.dummy.DataDummyHelper
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.domain.movie.GetMovieDetailUseCase
import com.nadhif.hayazee.domain.tv.GetTvDetailUseCase
import com.nadhif.hayazee.tv.tv_detail.TvDetailViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
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
class TvDetailViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var tvDetailViewModel: TvDetailViewModel

    @Mock
    private lateinit var getTvDetailUseCase: GetTvDetailUseCase


    @Before
    fun setup() {
        tvDetailViewModel = TvDetailViewModel(
            getTvDetailUseCase
        )

    }

    @Test
    fun `get movie tv`() = runTest {
        val tvDummy = DataDummyHelper.getDummyTv()

        `when`(getTvDetailUseCase("191")).then {
            flow {
                emit(ResponseState.SuccessWithData(tvDummy))
            }
        }

        val states = mutableListOf<ResponseState<Movie?>>()
        val job = launch(UnconfinedTestDispatcher()) {
            tvDetailViewModel.tvDetailState.toList(states)
        }

        tvDetailViewModel.getTvDetail("191")

        val firstState = states[0] is ResponseState.Loading
        val secondState = states[1] is ResponseState.SuccessWithData


        assertEquals(true, firstState)
        assertEquals(true, secondState)
        assertNotNull((states[1] as ResponseState.SuccessWithData).data)

        job.cancel()
    }
}