package com.project.core.data.source.remote.network

import com.project.core.data.source.remote.response.CreatorPositionResponse
import com.project.core.data.source.remote.response.GamesDetailResponse
import com.project.core.data.source.remote.response.GamesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun getGames(
        @Query("key") apiKey: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("search") search: String,
    ): GamesResponse

    @GET("creators")
    suspend fun getCreators(
        @Query("key") apiKey: String
    ): CreatorPositionResponse

    @GET("games/{gameId}")
    suspend fun getGameDetails(
        @Path("gameId") gameId: Int,
        @Query("key") apiKey: String
    ): GamesDetailResponse
}