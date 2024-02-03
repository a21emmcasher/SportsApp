package com.example.deportes4

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("idTeam")
    val idTeam: String,

    @SerializedName("strTeam")
    val strTeam: String
)
