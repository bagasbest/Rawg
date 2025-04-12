package com.project.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.core.domain.usecase.GamesUseCase

class FavoriteViewModel(gamesRepository: GamesUseCase): ViewModel() {
    val favoriteGames = gamesRepository.getFavoriteGames().asLiveData()
}