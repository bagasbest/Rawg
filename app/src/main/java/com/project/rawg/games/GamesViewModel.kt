package com.project.rawg.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.core.domain.usecase.GamesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

@ExperimentalCoroutinesApi
@FlowPreview
class GamesViewModel(private val gamesRepository: GamesUseCase) : ViewModel() {
    val queryChannel = MutableStateFlow("")

    val searchResult = queryChannel
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            gamesRepository.getAllGames(query)
        }
        .asLiveData()
}
