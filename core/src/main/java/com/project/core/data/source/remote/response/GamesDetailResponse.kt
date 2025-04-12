package com.project.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GamesDetailResponse(
	@field:SerializedName("id")
	val gamesId: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("website")
	val website: String,

	@field:SerializedName("reddit_url")
	val redditUrl: String,

	@field:SerializedName("metacritic_url")
	val metaCriticUrl: String,
)
