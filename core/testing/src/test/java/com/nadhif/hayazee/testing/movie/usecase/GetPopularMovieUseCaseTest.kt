package com.nadhif.hayazee.testing.movie.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.domain.movie.GetPopularMoviesUseCase
import com.nadhif.hayazee.testing.movie.repository.FakeRemoteMovieRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetPopularMovieUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val fakeRemoteMovieRepository = FakeRemoteMovieRepository()
    private val getPopularMoviesUseCase = GetPopularMoviesUseCase(fakeRemoteMovieRepository)

    @Test
    fun `get popular movies`() = runTest {
        val states = mutableListOf<ResponseState<List<Movie>>>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            getPopularMoviesUseCase().toList(states)
        }


        assert(states[0] is ResponseState.Loading)
        assert(states[1] is ResponseState.SuccessWithData)
        assertNotNull((states[1] as ResponseState.SuccessWithData).data)
        assertEquals(6, (states[1] as ResponseState.SuccessWithData).data.size)

        collectJob.cancel()
    }

}



