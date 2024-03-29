package com.example.gamershub.domain.models

data class GenreDetails(
    val id: Int,
    val name: String,
    val slug: String,
    val gamesCount: Int,
    val imageBackground: String?,
    val description: String?
)