package com.example.deportes4

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    //primera API
    @GET("api/v1/json/3/search_all_teams.php")
    fun getAllTeams(
        @Query("s") sport: String,
        @Query("c") country: String
    ): Call<TeamResponse>

    //segunda API
    @GET("api/v1/json/3/eventsseason.php")
    fun getEventsForSeason(
        @Query("id") teamId: Int,
        @Query("s") season: String
    ): Call<EventResponse>

    //tercera api
    @GET("api/v1/json/3/eventresults.php")
    fun getEventResults(
        @Query("id") eventId: Int
    ): Call<EventResultsResponse>
}

    /*
    @GET("api/v1/json/3/eventslast.php")
    fun getLastEvents(
        @Query("id") teamId: String
    ): Call<EventResponse>

    @GET("api/v1/json/3/events.php")
    fun getEventsForTeam(
        @Query("id") teamId: String
    ): Call<EventResponse>

    @GET("api/v1/json/3/eventdetails.php")
    fun getEventDetails(
        @Query("id") eventId: Int
    ): Call<EventDetailsResponse>
}*/



