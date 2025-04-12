package com.project.core.domain.usecase

import com.project.core.data.Resource
import com.project.core.domain.model.Creator
import com.project.core.domain.model.DetailGames
import com.project.core.domain.model.Games
import kotlinx.coroutines.flow.Flow

interface GamesUseCase {
    fun getAllGames(query: String): Flow<Resource<List<Games>>>
    fun getFavoriteGames(): Flow<List<Games>>
    fun setFavoriteGames(games: Games, state: Boolean)
    fun getDetailGames(gamesId: Int): Flow<Resource<List<DetailGames>>>
    fun getCreator(): Flow<Resource<List<Creator>>>
    fun checkFavoriteGames(gamesId: Int): Flow<Boolean>
}