package com.example.deportes4

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("idTeam")
    val idTeam: String,

    @SerializedName("strTeam")
    val strTeam: String,

    @SerializedName("strTeamBadge")
    val strTeamBadge: String // Add a field for the team badge URL
)
