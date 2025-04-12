package com.project.rawg.creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.core.domain.usecase.GamesUseCase

class CreatorViewModel(gamesRepository: GamesUseCase): ViewModel() {
    val creator = gamesRepository.getCreator().asLiveData()
}