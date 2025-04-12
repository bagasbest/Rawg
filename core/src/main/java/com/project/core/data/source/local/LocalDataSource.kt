package com.project.core.data.source.local

import com.project.core.data.source.local.entity.CreatorEntity
import com.project.core.data.source.local.entity.DetailGamesEntity
import com.project.core.data.source.local.entity.FavoritesEntity
import com.project.core.data.source.local.entity.GamesEntity
import com.project.core.data.source.local.room.GamesDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource internal constructor(private val gamesDao: GamesDao) {

    fun getAllGames() = gamesDao.getAllGames()

    fun getFavoriteGames() = gamesDao.getFavoriteGames()

    suspend fun insertGames(games: List<GamesEntity>) {
        gamesDao.insertGames(games)
    }

    fun setFavoriteGames(games: GamesEntity, newState: Boolean) {
        games.isFavorite = newState
        gamesDao.updateGames(games)

        val favoriteGame = FavoritesEntity(
            gamesId = games.gamesId,
            name = games.name,
            released = games.released,
            rating = games.rating,
            ratingTop = games.ratingTop,
            ratingsCount = games.ratingsCount,
            parentPlatform = games.parentPlatform,
            genres = games.genres,
            tags = games.tags,
            esrbRating = games.esrbRating,
            shortScreenshots = games.shortScreenshots,
            backgroundImage = games.backgroundImage
        )
        if (newState) {
            gamesDao.insertFavorite(favoriteGame)
        } else {
            gamesDao.deleteFavorite(favoriteGame)
        }
    }

    fun getDetailGames(gamesId: Int) = gamesDao.getDetailGames(gamesId)

    suspend fun insertDetailGames(games: List<DetailGamesEntity>) {
        gamesDao.insertDetailGames(games)
    }

    fun getCreator() = gamesDao.getCreator()

    suspend fun insertCreator(creator: List<CreatorEntity>) {
        gamesDao.insertCreator(creator)
    }

    suspend fun deleteAllGames() = gamesDao.deleteAllGames()

    fun checkFavoriteGames(gamesId: Int): Flow<Boolean> =
        gamesDao.checkFavoriteGames(gamesId)

}