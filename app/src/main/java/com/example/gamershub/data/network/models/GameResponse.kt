package com.example.gamershub.data.network.models

import com.squareup.moshi.Json

data class GameResponse(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "slug") val slug: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "released") val released: String,
    @field:Json(name = "background_image") val backgroundImage: String?,
    @field:Json(name = "website") val website: String?,
    @field:Json(name = "rating") val rating: Double?
)
