package com.project.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Games(
    val gamesId: Int,
    val name: String,
    val released: String,
    val backgroundImage: String,
    val rating: Double,
    val ratingTop: Int,
    val ratingsCount: Int,
    val parentPlatform: String,
    val genres: String,
    val tags: String,
    val esrbRating: String,
    val shortScreenshots: String,
    var isFavorite: Boolean = false
): Parcelable

data class DetailGames(
    val gamesId: Int,
    val description: String,
    val website: String,
    val redditUrl: String,
    val metaCriticUrl: String,
)

data class Creator(
    val creatorId: Int,
    val name: String,
    val image: String,
    val position: String,
    val games: String,
)
