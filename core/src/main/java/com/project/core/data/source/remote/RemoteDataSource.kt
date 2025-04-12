package com.project.core.data.source.remote

import com.project.core.data.source.remote.network.ApiResponse
import com.project.core.data.source.remote.network.ApiService
import com.project.core.data.source.remote.response.CreatorPositionItem
import com.project.core.data.source.remote.response.GamesDetailResponse
import com.project.core.data.source.remote.response.GamesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    @ExperimentalCoroutinesApi
    @FlowPreview
    fun getAllGames(query: String): Flow<ApiResponse<List<GamesItem>>> {
        return flow {
            try {
                val response = apiService.getGames(
                    apiKey = API_KEY,
                    page = 1,
                    pageSize = 5,
                    search = query
                )
                val dataArray = response.results!!
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }


    fun getCreatorPosition(): Flow<ApiResponse<List<CreatorPositionItem>>> {
        return flow {
            try {
                val response = apiService.getCreators(
                    apiKey = API_KEY
                )
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getGamesDetail(gamesId: Int): Flow<ApiResponse<List<GamesDetailResponse>>> {
        return flow {
            try {
                val response = apiService.getGameDetails(
                    gameId = gamesId,
                    apiKey = API_KEY
                )
                val dataArray = listOf(response)
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        const val API_KEY = "b463f3222b5c4bb7988648aa2edd30cc"
    }
}