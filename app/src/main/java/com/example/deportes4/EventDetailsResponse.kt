package com.example.deportes4

data class EventDetailsResponse(
    val events: List<EventDetails>
)

data class EventDetails(
    val idEvent: Int,
    val strEvent: String,
    val strDescriptionEN: String
)
