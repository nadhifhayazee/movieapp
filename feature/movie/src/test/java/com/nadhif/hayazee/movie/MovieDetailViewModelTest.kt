package com.nadhif.hayazee.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nadhif.hayazee.common.dummy.DataDummyHelper
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.domain.movie.GetMovieDetailUseCase
import com.nadhif.hayazee.movie.movie_detail.MovieDetailViewModel
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
class MovieDetailViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    @Mock
    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase


    @Before
    fun setup() {
        movieDetailViewModel = MovieDetailViewModel(
            getMovieDetailUseCase
        )

    }

    @Test
    fun `get movie detail`() = runTest {
        val movieDummy = DataDummyHelper.getDummyMovie()

        `when`(getMovieDetailUseCase("191")).then {
            flow {
                emit(ResponseState.SuccessWithData(movieDummy))
            }
        }

        val states = mutableListOf<ResponseState<Movie?>>()
        val job = launch(UnconfinedTestDispatcher()) {
            movieDetailViewModel.movieDetailState.toList(states)
        }

        movieDetailViewModel.getMovieDetail("191")

        val firstState = states[0] is ResponseState.Loading
        val secondState = states[1] is ResponseState.SuccessWithData


        assertEquals(true, firstState)
        assertEquals(true, secondState)
        assertNotNull((states[1] as ResponseState.SuccessWithData).data)

        job.cancel()
    }
}