package com.rodrigorods.events.usecase

import com.rodrigorods.events.model.Event
import com.rodrigorods.events.repository.EventListRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class EventListUseCaseImplTest {

    private val repository = mockk<EventListRepository>()
    private val eventListUseCase = EventListUseCaseImpl(repository)

    @Test
    fun getEventList_returnExpectedListOfEvents() = runBlocking {
        val expectedResult = Result.success(emptyList<Event>())
        coEvery { repository.getEventList() } returns expectedResult

        assertEquals(eventListUseCase.getEventList(), expectedResult)
    }
}
