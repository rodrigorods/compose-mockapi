package com.rodrigorods.events.data.repository

import com.rodrigorods.events.EventApi
import com.rodrigorods.events.repository.EventListRepository

class EventListRepositoryImpl(
    private val api: EventApi
) : EventListRepository {
    override suspend fun getEventList() = safeApiCall {
        api.getCharacters()
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.success(apiCall.invoke())
        } catch (throwable: Throwable) {
            Result.failure(throwable)
        }
    }
}