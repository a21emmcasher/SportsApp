package com.example.deportes4

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/api/v1/json/3/eventsseason.php")
    fun getSeasonEvents(@Query("id") teamId: Int, @Query("s") season: String): Call<EventResponse>

    @GET("/api/v1/json/3/eventdetails.php")
    fun getEventDetails(@Query("id") eventId: Int): Call<EventDetailsResponse>
}


