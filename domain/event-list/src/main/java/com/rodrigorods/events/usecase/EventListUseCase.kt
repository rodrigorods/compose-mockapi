package com.rodrigorods.events.usecase

import com.rodrigorods.events.model.Event
import com.rodrigorods.events.repository.EventListRepository

interface EventListUseCase {
    suspend fun getEventList(): Result<List<Event>>
}

class EventListUseCaseImpl(
    private val repository: EventListRepository
): EventListUseCase {
    override suspend fun getEventList(): Result<List<Event>> {
        return repository.getEventList()
    }
}