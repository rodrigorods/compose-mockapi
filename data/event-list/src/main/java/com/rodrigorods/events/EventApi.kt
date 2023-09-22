package com.rodrigorods.events

import com.rodrigorods.events.model.Event
import retrofit2.http.GET
import retrofit2.http.Path

interface EventApi {
    @GET("/api/events")
    suspend fun getEvents(): List<Event>

    @GET("/api/events/{event-id}")
    suspend fun getEventDetail(
        @Path("event-id") id: String
    ): Event
}
