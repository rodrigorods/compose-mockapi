package com.rodrigorods.events

import com.rodrigorods.events.model.Event
import retrofit2.http.GET

interface EventApi {
    @GET("/api/events")
    suspend fun getCharacters(): List<Event>
}
