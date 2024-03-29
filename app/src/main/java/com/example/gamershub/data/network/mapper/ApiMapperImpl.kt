package com.example.gamershub.data.network.mapper

import com.example.gamershub.data.network.models.GameResponse
import com.example.gamershub.data.network.models.GamesByGenresResponse
import com.example.gamershub.data.network.models.GenreDetailsResponse
import com.example.gamershub.data.network.models.GenresResponse
import com.example.gamershub.domain.models.Game
import com.example.gamershub.domain.models.GamesByGenres
import com.example.gamershub.domain.models.GenreDetails
import com.example.gamershub.domain.models.Genre
import retrofit2.Response

class ApiMapperImpl : ApiMapper {

    override fun toGenres(response: GenresResponse): Genre {
        return Genre(response.count, response.next, response.previous, toGenreDetails(response.results))
    }

    private fun toGenreDetails(genreDetailsResponse: List<GenreDetailsResponse>): List<GenreDetails> {
        return genreDetailsResponse.map { GenreDetails(it.id, it.name, it.slug, it.gamesCount, it.imageBackground, it.description) }
    }

    override fun toGamesForGenres(response: Response<GamesByGenresResponse>): GamesByGenres {
        if (response.isSuccessful) {
            val result = response.body()
            return GamesByGenres(result?.next.orEmpty(), toGames(result?.results.orEmpty()))
        } else {
            throw Exception(response.message())
        }
    }

    override fun toGames(response: GameResponse): Game {
        return Game(
            response.id,
            response.slug,
            response.name,
            response.description.orEmpty(),
            response.released,
            response.backgroundImage.orEmpty(),
            response.website.orEmpty(),
            response.rating ?: 0.0
        )
    }

    private fun toGames(
        gameResponses: List<GameResponse>
    ) = gameResponses.map { game ->
        Game(
            game.id, game.slug, game.name, game.description ?: "", game.released, game.backgroundImage ?: "", game.website ?: "", game.rating ?: 0.0
        )
    }
}