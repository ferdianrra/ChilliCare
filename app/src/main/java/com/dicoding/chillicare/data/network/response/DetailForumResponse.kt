package com.dicoding.chillicare.data.network.response

import com.google.gson.annotations.SerializedName

data class DetailForumResponse(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("userId")
	val userId: String
)
