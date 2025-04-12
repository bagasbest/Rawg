package com.project.core.utils

import com.project.core.data.source.local.entity.CreatorEntity
import com.project.core.data.source.local.entity.DetailGamesEntity
import com.project.core.data.source.local.entity.FavoritesEntity
import com.project.core.data.source.local.entity.GamesEntity
import com.project.core.data.source.remote.response.CreatorPositionItem
import com.project.core.data.source.remote.response.GamesDetailResponse
import com.project.core.data.source.remote.response.GamesItem
import com.project.core.domain.model.Creator
import com.project.core.domain.model.DetailGames
import com.project.core.domain.model.Games

object DataMapper {
    fun mapResponseToEntities(input: List<GamesItem>): List<GamesEntity> {
        return input.map { item ->
            val parentPlatforms = item.parentPlatforms?.joinToString(", ") { it.platform?.name.toString() } ?: ""
            val genres = item.genres?.joinToString(", ") { it.name.toString() } ?: ""
            val tags = item.tags?.joinToString(", ") { it.name.toString() } ?: ""
            val esrbRating = item.esrbRating?.name ?: ""
            val shortScreenshots = item.shortScreenshots?.joinToString(", ") { it.image.toString() } ?: ""
            val backgroundImage = item.backgroundImage ?: ""
            val gamesId = item.id ?: 0
            val gamesName = item.name ?: ""
            val released = item.released ?: ""
            val rating = item.rating ?: 0.0
            val ratingTop = item.ratingTop ?: 0
            val ratingCount = item.ratingsCount ?: 0

            GamesEntity(
                gamesId = gamesId,
                name = gamesName,
                released = released,
                backgroundImage = backgroundImage,
                rating = rating,
                ratingTop = ratingTop,
                ratingsCount = ratingCount,
                parentPlatform = parentPlatforms,
                genres = genres,
                tags = tags,
                esrbRating = esrbRating,
                shortScreenshots = shortScreenshots,
                isFavorite = false
            )
        }
    }


    fun mapEntitiesToDomain(input: List<GamesEntity>): List<Games> =
        input.map {
            Games(
                gamesId = it.gamesId,
                name = it.name,
                released = it.released,
                backgroundImage = it.backgroundImage,
                rating = it.rating,
                ratingTop = it.ratingTop,
                ratingsCount = it.ratingsCount,
                parentPlatform = it.parentPlatform,
                genres = it.genres,
                tags = it.tags,
                esrbRating = it.esrbRating,
                shortScreenshots = it.shortScreenshots,
                isFavorite = it.isFavorite
            )
        }

    fun mapEntitiesToDomainFavorite(input: List<FavoritesEntity>): List<Games> =
        input.map {
            Games(
                gamesId = it.gamesId,
                name = it.name,
                released = it.released,
                backgroundImage = it.backgroundImage,
                rating = it.rating,
                ratingTop = it.ratingTop,
                ratingsCount = it.ratingsCount,
                parentPlatform = it.parentPlatform,
                genres = it.genres,
                tags = it.tags,
                esrbRating = it.esrbRating,
                shortScreenshots = it.shortScreenshots
            )
        }

    fun mapDomainToEntity(input: Games) = GamesEntity(
        gamesId = input.gamesId,
        name = input.name,
        released = input.released,
        backgroundImage = input.backgroundImage,
        rating = input.rating,
        ratingTop = input.ratingTop,
        ratingsCount = input.ratingsCount,
        parentPlatform = input.parentPlatform,
        genres = input.genres,
        tags = input.tags,
        esrbRating = input.esrbRating,
        shortScreenshots = input.shortScreenshots,
        isFavorite = input.isFavorite
    )

    fun mapResponseToEntitiesGamesDetail(response: List<GamesDetailResponse>): List<DetailGamesEntity> {
        val gamesDetailList = ArrayList<DetailGamesEntity>()
        response.map {
            val games = DetailGamesEntity(
                gamesId = it.gamesId,
                description = it.description,
                redditUrl = it.redditUrl,
                website = it.website,
                metaCriticUrl = it.metaCriticUrl
            )
            gamesDetailList.add(games)
        }
        return gamesDetailList
    }

    fun mapEntitiesToDomainGamesDetail(input: List<DetailGamesEntity>): List<DetailGames> =
        input.map {
            DetailGames(
                gamesId = it.gamesId ,
                description = it.description ,
                redditUrl = it.redditUrl,
                website = it.website,
                metaCriticUrl = it.metaCriticUrl
            )
        }

    fun mapEntitiesToDomainCreator(input: List<CreatorEntity>): List<Creator> =
        input.map {
            Creator(
                creatorId = it.creatorId,
                name = it.name,
                image = it.image,
                position = it.position,
                games = it.games
            )
        }

    fun mapResponseToEntitiesCreator(input: List<CreatorPositionItem>): List<CreatorEntity> {
        val creatorList = ArrayList<CreatorEntity>()
        input.map {
            val creator = CreatorEntity(
                creatorId = it.id,
                name = it.name,
                image = it.imageBackground,
                position = it.position?.joinToString(", ") { pos -> pos.name } ?: "",
                games = it.games?.joinToString(", ") { game -> game.name } ?: ""
            )
            creatorList.add(creator)
        }
        return creatorList
    }
}