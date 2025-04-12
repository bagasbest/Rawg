package com.project.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.project.core.data.source.local.entity.CreatorEntity
import com.project.core.data.source.local.entity.DetailGamesEntity
import com.project.core.data.source.local.entity.FavoritesEntity
import com.project.core.data.source.local.entity.GamesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {

    @Query("SELECT * FROM games")
    fun getAllGames(): Flow<List<GamesEntity>>

    @Query("SELECT * FROM favorite")
    fun getFavoriteGames(): Flow<List<FavoritesEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGames(games: List<GamesEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favoriteGames: FavoritesEntity)

    @Update
    fun updateGames(games: GamesEntity)

    @Delete
    fun deleteFavorite(favoriteGames: FavoritesEntity)

    @Query("SELECT * FROM games_detail WHERE gamesId = :gamesId")
    fun getDetailGames(gamesId: Int): Flow<List<DetailGamesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailGames(games: List<DetailGamesEntity>)

    @Query("SELECT * FROM creator")
    fun getCreator(): Flow<List<CreatorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreator(creator: List<CreatorEntity>)

    @Query("DELETE FROM games")
    suspend fun deleteAllGames()

    @Query("SELECT EXISTS(SELECT 1 FROM favorite WHERE gamesId = :gamesId)")
    fun checkFavoriteGames(gamesId: Int): Flow<Boolean>
}