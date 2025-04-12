package com.project.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.core.data.source.local.entity.CreatorEntity
import com.project.core.data.source.local.entity.DetailGamesEntity
import com.project.core.data.source.local.entity.FavoritesEntity
import com.project.core.data.source.local.entity.GamesEntity

@Database(
    entities = [GamesEntity::class, DetailGamesEntity::class, CreatorEntity::class, FavoritesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GamesDatabase : RoomDatabase() {
    abstract fun gamesDao(): GamesDao
}