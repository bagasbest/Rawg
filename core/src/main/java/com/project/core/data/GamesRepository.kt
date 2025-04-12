package com.project.core.data

import com.project.core.data.source.local.LocalDataSource
import com.project.core.data.source.remote.RemoteDataSource
import com.project.core.data.source.remote.network.ApiResponse
import com.project.core.data.source.remote.response.CreatorPositionItem
import com.project.core.data.source.remote.response.GamesDetailResponse
import com.project.core.data.source.remote.response.GamesItem
import com.project.core.domain.model.Creator
import com.project.core.domain.model.DetailGames
import com.project.core.domain.model.Games
import com.project.core.domain.repository.IGamesRepository
import com.project.core.utils.AppExecutors
import com.project.core.utils.DataMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GamesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
): IGamesRepository {

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun getAllGames(query: String): Flow<Resource<List<Games>>> =
        object : NetworkBoundResource<List<Games>, List<GamesItem>>() {
            override fun loadFromDb(): Flow<List<Games>> {
                return localDataSource.getAllGames().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Games>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<GamesItem>>> {
                return remoteDataSource.getAllGames(query)
            }

            override suspend fun saveCallResult(data: List<GamesItem>) {
                val gamesList = DataMapper.mapResponseToEntities(data)
                localDataSource.deleteAllGames()
                localDataSource.insertGames(gamesList)
            }

        }.asFlow()

    override fun getFavoriteGames(): Flow<List<Games>> {
       return localDataSource.getFavoriteGames().map {
           DataMapper.mapEntitiesToDomainFavorite(it)
       }
    }

    override fun setFavoriteGames(
        games: Games,
        newState: Boolean
    ) {
        val gamesEntity = DataMapper.mapDomainToEntity(games)
        appExecutors.diskIO().execute { localDataSource.setFavoriteGames(gamesEntity, newState) }
    }

    override fun getDetailGames(gamesId: Int): Flow<Resource<List<DetailGames>>> =
        object : NetworkBoundResource<List<DetailGames>, List<GamesDetailResponse>>() {
            override fun loadFromDb(): Flow<List<DetailGames>> =
                localDataSource.getDetailGames(gamesId).map { DataMapper.mapEntitiesToDomainGamesDetail(it) }

            override fun shouldFetch(data: List<DetailGames>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<GamesDetailResponse>>> =
                remoteDataSource.getGamesDetail(gamesId)

            override suspend fun saveCallResult(data: List<GamesDetailResponse>) {
                val games = DataMapper.mapResponseToEntitiesGamesDetail(data)
                localDataSource.insertDetailGames(games)
            }

        }.asFlow()

    override fun getCreator(): Flow<Resource<List<Creator>>> =
        object : NetworkBoundResource<List<Creator>, List<CreatorPositionItem>>() {
            override fun loadFromDb(): Flow<List<Creator>> {
                return localDataSource.getCreator().map { DataMapper.mapEntitiesToDomainCreator(it) }
            }

            override fun shouldFetch(data: List<Creator>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<CreatorPositionItem>>> =
                remoteDataSource.getCreatorPosition()

            override suspend fun saveCallResult(data: List<CreatorPositionItem>) {
                val creator = DataMapper.mapResponseToEntitiesCreator(data)
                localDataSource.insertCreator(creator)
            }

        }.asFlow()

    override fun checkFavoriteGames(gamesId: Int): Flow<Boolean> =
        localDataSource.checkFavoriteGames(gamesId)

}