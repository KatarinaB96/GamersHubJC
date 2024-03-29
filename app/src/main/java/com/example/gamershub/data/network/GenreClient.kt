package com.example.gamershub.data.network

import com.example.gamershub.domain.models.Game
import com.example.gamershub.domain.models.GamesByGenres
import com.example.gamershub.domain.models.Genre

interface GenreClient {
    suspend fun getGenres(): Genre
    suspend fun getGamesForGenres(genreIds: String, pageNumber: Int): GamesByGenres
    suspend fun getGameDetailsById(gameId: Int): Game
}