package com.example.gamershub.presentation.game

import com.example.gamershub.domain.models.Game

data class GameState(
    val game: Game? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

