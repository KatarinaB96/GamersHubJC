package com.example.gamershub.presentation.genre

import com.example.gamershub.domain.models.Genre

data class GenreState(
    val genre: Genre? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
