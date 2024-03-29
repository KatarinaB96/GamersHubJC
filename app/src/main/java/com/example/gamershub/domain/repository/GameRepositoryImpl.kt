package com.example.gamershub.domain.repository

import com.example.gamershub.data.network.GenreClient
import com.example.gamershub.domain.models.Game
import com.example.gamershub.domain.models.GamesByGenres
import com.example.gamershub.domain.models.Genre
import com.example.gamershub.domain.utils.Resource
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val genreClient: GenreClient
) : GameRepository {
    override suspend fun getGenres(): Resource<Genre> {
        return try {
            Resource.Success(
                genreClient.getGenres()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getGamesForGenres(genreIds: String, pageNumber: Int): Resource<GamesByGenres> {
        return try {
            Resource.Success(
                genreClient.getGamesForGenres(genreIds, pageNumber)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getGames(gameId: Int): Resource<Game> {
        return try {
            Resource.Success(
                genreClient.getGameDetailsById(gameId)
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

}