package com.example.deportes4

data class EventResults(
    val result1: String,
    val result2: String,
)

data class EventResultsResponse(
    val results: List<EventResults>
)
