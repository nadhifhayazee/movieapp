package com.nadhif.hayazee.testing.tv.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nadhif.hayazee.common.model.ResponseState
import com.nadhif.hayazee.domain.tv.GetNowPlayingTvUseCase
import com.nadhif.hayazee.testing.tv.repository.FakeRemoteTvRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetNowPlayingTvUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val fakeRemoteTvRepository = FakeRemoteTvRepository()
    private val getNowPlayingTvUseCase = GetNowPlayingTvUseCase(fakeRemoteTvRepository)

    @Test
    fun `get now playing tv`() = runTest {

        val states = mutableListOf<ResponseState<List<CarouselItem>>>()

        val collectJob = launch(UnconfinedTestDispatcher()) {
            getNowPlayingTvUseCase().toList(states)
        }


        assert(states[0] is ResponseState.Loading)

        assert(states[1] is ResponseState.SuccessWithData)

        assertNotNull((states[1] as ResponseState.SuccessWithData).data)

        assertEquals(8, (states[1] as ResponseState.SuccessWithData).data.size)



        collectJob.cancel()
    }

}



