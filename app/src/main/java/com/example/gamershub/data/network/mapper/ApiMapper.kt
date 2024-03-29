package com.example.gamershub.data.network.mapper

import com.example.gamershub.data.network.models.GameResponse
import com.example.gamershub.data.network.models.GamesByGenresResponse
import com.example.gamershub.data.network.models.GenresResponse
import com.example.gamershub.domain.models.Game
import com.example.gamershub.domain.models.GamesByGenres
import com.example.gamershub.domain.models.Genre
import retrofit2.Response

interface ApiMapper {
    fun toGenres(response: GenresResponse): Genre
    fun toGamesForGenres(response: Response<GamesByGenresResponse>): GamesByGenres
    fun toGames(response: GameResponse): Game
}