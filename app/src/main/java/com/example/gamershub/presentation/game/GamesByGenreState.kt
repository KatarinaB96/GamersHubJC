package com.example.gamershub.presentation.game

import com.example.gamershub.domain.models.GamesByGenres

data class GamesByGenreState(
    val game: GamesByGenres? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
