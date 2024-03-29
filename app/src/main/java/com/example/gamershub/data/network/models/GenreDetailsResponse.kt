package com.example.gamershub.data.network.models

import com.squareup.moshi.Json

data class GenreDetailsResponse(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "slug") val slug: String,
    @field:Json(name = "games_count") val gamesCount: Int,
    @field:Json(name = "image_background") val imageBackground: String?,
    @field:Json(name = "description") val description: String?,
)
