package com.rodrigorods.events.model

data class Event(
    val id: Long,
    val date: Long,
    val title: String,
    val description: String,
    val price: Float,
    val latitude: Double,
    val longitude: Double,
    val image: String,
)
