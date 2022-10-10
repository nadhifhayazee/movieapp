package com.nadhif.hayazee.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nadhif.hayazee.common.dummy.DataDummyHelper
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.domain.movie.GetNowPlayingMoviesUseCase
import com.nadhif.hayazee.domain.movie.GetPopularMoviesUseCase
import com.nadhif.hayazee.movie.movie_list.MovieViewModel
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
class MovieViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var movieViewModel: MovieViewModel

    @Mock
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Mock
    private lateinit var getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase

    @Before
    fun setup() {
        movieViewModel = MovieViewModel(
            getPopularMoviesUseCase, getNowPlayingMoviesUseCase
        )

    }

    @Test
    fun `get popular movies`() = runTest {
        val popularMoviesDummy = DataDummyHelper.getPopularMovieDummies()

        `when`(getPopularMoviesUseCase()).then {
            flow {
                emit(ResponseState.SuccessWithData(popularMoviesDummy))
            }
        }

        val states = mutableListOf<ResponseState<List<Movie>>>()
        val job = launch(UnconfinedTestDispatcher()) {
            movieViewModel.popularMoviesState.toList(states)
        }

        movieViewModel.getPopularMovies()

        val firstState = states[0] is ResponseState.Loading
        val secondState = states[1] is ResponseState.SuccessWithData


        assertEquals(true, firstState)
        assertEquals(true, secondState)
        assertNotNull((states[1] as ResponseState.SuccessWithData).data)
        assertEquals(6, (states[1] as ResponseState.SuccessWithData).data.size)

        job.cancel()
    }

    @Test
    fun `get now playing movies`() = runTest {
        val nowPlayingMovieDummy = DataDummyHelper.getPlayingMovieDummies()

        `when`(getNowPlayingMoviesUseCase()).then {
            flow {
                val carouselItems = nowPlayingMovieDummy.map {
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
            movieViewModel.nowPlayingMovieState.toList(states)
        }

        movieViewModel.getNowPlayingMovies()

        val firstState = states[0] is ResponseState.Loading
        val secondState = states[1] is ResponseState.SuccessWithData


        assertEquals(true, firstState)
        assertEquals(true, secondState)
        assertNotNull((states[1] as ResponseState.SuccessWithData).data)
        assertEquals(8, (states[1] as ResponseState.SuccessWithData).data.size)

        job.cancel()
    }
}