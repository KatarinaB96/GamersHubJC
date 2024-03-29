package com.example.gamershub.data.network

import com.example.gamershub.BuildConfig
import com.example.gamershub.data.network.mapper.ApiMapper
import com.example.gamershub.domain.models.Game
import com.example.gamershub.domain.models.GamesByGenres
import com.example.gamershub.domain.models.Genre

class GenreClientImpl(
    private val genresApi: GenresApi,
    private val apiMapper: ApiMapper
) : GenreClient {
    override suspend fun getGenres(): Genre {
        val result = genresApi.getGenres(BuildConfig.API_KEY)
        return apiMapper.toGenres(result)
    }

    override suspend fun getGamesForGenres(genreIds: String, pageNumber: Int): GamesByGenres {
        val result = genresApi.getGamesForGenres(BuildConfig.API_KEY, genreIds, 40, pageNumber)
        return apiMapper.toGamesForGenres(result)
    }

    override suspend fun getGameDetailsById(gameId: Int): Game {
        val result = genresApi.getGame(gameId, BuildConfig.API_KEY)
        return apiMapper.toGames(result)
    }

}