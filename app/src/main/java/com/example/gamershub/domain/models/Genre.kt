package com.example.gamershub.domain.models

data class Genre(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<GenreDetails>
)
