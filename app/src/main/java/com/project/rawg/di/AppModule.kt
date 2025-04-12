package com.project.rawg.di

import com.project.core.domain.usecase.GamesInteractor
import com.project.core.domain.usecase.GamesUseCase
import com.project.rawg.creator.CreatorViewModel
import com.project.rawg.detail.DetailGamesViewModel
import com.project.rawg.games.GamesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GamesUseCase> { GamesInteractor(get()) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val viewModelModule = module {
    viewModel { GamesViewModel(get()) }
    viewModel { DetailGamesViewModel(get())}
    viewModel { CreatorViewModel(get()) }
}