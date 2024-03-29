package com.example.gamershub.data.network.models

import com.squareup.moshi.Json

data class GamesByGenresResponse(
    @field:Json(name = "next") val next: String,
    @field:Json(name = "previous") val previous: String,
    @field:Json(name = "results") val results: List<GameResponse>,
)
