package com.example.gamershub.domain.repository

import com.example.gamershub.domain.models.Game
import com.example.gamershub.domain.models.GamesByGenres
import com.example.gamershub.domain.models.Genre
import com.example.gamershub.domain.utils.Resource

interface GameRepository {
    suspend fun getGenres(): Resource<Genre>
    suspend fun getGamesForGenres(genreIds: String, pageNumber: Int): Resource<GamesByGenres>

    suspend fun getGames(gameId: Int): Resource<Game>
}