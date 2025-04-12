package com.project.rawg.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.core.domain.model.Games
import com.project.core.domain.usecase.GamesUseCase

class DetailGamesViewModel(private val gamesRepository: GamesUseCase): ViewModel() {
    fun setFavoriteGames(games: Games, newStatus: Boolean) = gamesRepository.setFavoriteGames(games, newStatus)
    fun getDetailGames(gamesId: Int) = gamesRepository.getDetailGames(gamesId).asLiveData()
    fun checkFavoriteGames(gamesId: Int) = gamesRepository.checkFavoriteGames(gamesId).asLiveData()
}