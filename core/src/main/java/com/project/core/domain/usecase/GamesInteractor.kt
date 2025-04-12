package com.project.core.domain.usecase

import com.project.core.data.Resource
import com.project.core.domain.model.Creator
import com.project.core.domain.model.DetailGames
import com.project.core.domain.model.Games
import com.project.core.domain.repository.IGamesRepository
import kotlinx.coroutines.flow.Flow

class GamesInteractor(private val gamesRepository: IGamesRepository): GamesUseCase {
    override fun getAllGames(query: String): Flow<Resource<List<Games>>> = gamesRepository.getAllGames(query)

    override fun getFavoriteGames(): Flow<List<Games>> = gamesRepository.getFavoriteGames()

    override fun setFavoriteGames(games: Games, state: Boolean) = gamesRepository.setFavoriteGames(games, state)

    override fun getDetailGames(gamesId: Int): Flow<Resource<List<DetailGames>>> = gamesRepository.getDetailGames(gamesId)

    override fun getCreator(): Flow<Resource<List<Creator>>> = gamesRepository.getCreator()

    override fun checkFavoriteGames(gamesId: Int): Flow<Boolean> = gamesRepository.checkFavoriteGames(gamesId)
}