package com.project.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CreatorPositionResponse(

    @field:SerializedName("next")
    val next: Any,

    @field:SerializedName("previous")
    val previous: Any,

    @field:SerializedName("count")
    val count: Int,

    @field:SerializedName("results")
    val results: List<CreatorPositionItem>
)

data class CreatorPositionItem(

    @field:SerializedName("games_count")
    val gamesCount: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("games")
    val games: List<CreatorGamesItem>?,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("image_background")
    val imageBackground: String,

    @field:SerializedName("slug")
    val slug: String,

    @field:SerializedName("positions")
    val position: List<Position>?
)

data class CreatorGamesItem(
    @field:SerializedName("name")
    val name: String
)

data class Position(
    @field:SerializedName("name")
    val name: String,
)
