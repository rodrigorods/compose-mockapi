package com.rodrigorods.events.repository

import com.rodrigorods.events.model.Event
import kotlin.Result
interface EventListRepository {
    suspend fun getEventList(): Result<List<Event>>
}