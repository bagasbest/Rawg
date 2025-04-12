package com.project.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GenreResponse(
	@field:SerializedName("results")
	val results: List<GenreItem>
)

data class GenreItem(
	@field:SerializedName("name")
	val name: String
)