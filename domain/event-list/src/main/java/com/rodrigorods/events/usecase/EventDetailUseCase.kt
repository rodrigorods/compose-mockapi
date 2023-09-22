package com.rodrigorods.events.usecase

import com.rodrigorods.events.model.Event
import com.rodrigorods.events.repository.EventListRepository

interface EventDetailUseCase {
    suspend fun getEventDetail(eventId: String): Result<Event>
}

class IEventDetailUseCaseImpl(
    private val repository: EventListRepository
): EventDetailUseCase {
    override suspend fun getEventDetail(eventId: String): Result<Event> {
        return repository.getEventDetail(eventId)
    }
}
