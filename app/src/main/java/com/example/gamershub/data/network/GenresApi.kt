package com.example.gamershub.data.network

import com.example.gamershub.data.network.models.GameResponse
import com.example.gamershub.data.network.models.GamesByGenresResponse
import com.example.gamershub.data.network.models.GenresResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GenresApi {

    @GET("genres")
    suspend fun getGenres(@Query("key") apiKey: String): GenresResponse

    @GET("games")
    suspend fun getGamesForGenres(
        @Query("key") apiKey: String,
        @Query("genres") genreIds: String,
        @Query("page_size") pageSize: Int,
        @Query("page") pageNumber: Int
    ): Response<GamesByGenresResponse>

    @GET("games/{id}")
    suspend fun getGame(@Path("id") id: Int, @Query("key") apiKey: String): GameResponse

}