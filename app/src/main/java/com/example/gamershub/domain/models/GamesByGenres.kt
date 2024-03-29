package com.example.gamershub.domain.models

data class GamesByGenres(
    val nextPage: String,
    val games: List<Game>
)
