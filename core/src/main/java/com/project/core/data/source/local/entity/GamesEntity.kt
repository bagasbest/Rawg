package com.project.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "games")
data class GamesEntity(
    @PrimaryKey
    val gamesId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "released")
    val released: String,

    @ColumnInfo(name = "background_image")
    val backgroundImage: String,

    @ColumnInfo(name = "rating")
    val rating: Double,

    @ColumnInfo(name = "rating_top")
    val ratingTop: Int,

    @ColumnInfo(name = "ratings_count")
    val ratingsCount: Int,

    @ColumnInfo(name = "parent_platforms")
    val parentPlatform: String,

    @ColumnInfo(name = "genres")
    val genres: String,

    @ColumnInfo(name = "tags")
    val tags: String,

    @ColumnInfo(name = "esrb_rating")
    val esrbRating: String,

    @ColumnInfo(name = "short_screenshots")
    val shortScreenshots: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false

): Parcelable

@Parcelize
@Entity(tableName = "favorite")
data class FavoritesEntity(
    @PrimaryKey
    val gamesId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "released")
    val released: String,

    @ColumnInfo(name = "background_image")
    val backgroundImage: String,

    @ColumnInfo(name = "rating")
    val rating: Double,

    @ColumnInfo(name = "rating_top")
    val ratingTop: Int,

    @ColumnInfo(name = "ratings_count")
    val ratingsCount: Int,

    @ColumnInfo(name = "parent_platforms")
    val parentPlatform: String,

    @ColumnInfo(name = "genres")
    val genres: String,

    @ColumnInfo(name = "tags")
    val tags: String,

    @ColumnInfo(name = "esrb_rating")
    val esrbRating: String,

    @ColumnInfo(name = "short_screenshots")
    val shortScreenshots: String

): Parcelable

@Entity(tableName = "games_detail")
data class DetailGamesEntity(
    @PrimaryKey
    val gamesId: Int,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "website")
    val website: String,

    @ColumnInfo(name = "reddit_url")
    val redditUrl: String,

    @ColumnInfo(name = "metacritic_url")
    val metaCriticUrl: String,
)

@Entity(tableName = "creator")
data class CreatorEntity(
    @PrimaryKey
    val creatorId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "position")
    val position: String,

    @ColumnInfo(name = "games")
    val games: String
)