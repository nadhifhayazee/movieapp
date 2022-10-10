package com.nadhif.hayazee.testing.tv.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nadhif.hayazee.common.dummy.DataDummyHelper
import com.nadhif.hayazee.common.model.Movie
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.domain.movie.GetMovieDetailUseCase
import com.nadhif.hayazee.domain.tv.GetTvDetailUseCase
import com.nadhif.hayazee.testing.movie.repository.FakeRemoteMovieRepository
import com.nadhif.hayazee.testing.tv.repository.FakeRemoteTvRepository
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
class GetTvDetailUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val fakeRemoteTvRepository = FakeRemoteTvRepository()

    private val getTvDetailUseCase = GetTvDetailUseCase(fakeRemoteTvRepository)

    @Test
    fun `get tv detail`() = runTest {
        val states = mutableListOf<ResponseState<Movie?>>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            getTvDetailUseCase("199").toList(states)
        }

        assert(states[0] is ResponseState.Loading)
        assert(states[1] is ResponseState.SuccessWithData)
        assertNotNull((states[1] as ResponseState.SuccessWithData).data)
        assertEquals(
            DataDummyHelper.getDummyTv().name,
            (states[1] as ResponseState.SuccessWithData).data?.name
        )

        collectJob.cancel()
    }

}



