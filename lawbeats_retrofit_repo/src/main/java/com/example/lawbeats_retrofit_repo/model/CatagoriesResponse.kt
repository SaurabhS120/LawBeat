package com.example.lawbeats_retrofit_repo.model

import com.google.gson.annotations.SerializedName

data class CatagoriesResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class DataItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("weight")
	val weight: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("subcat")
	val subcat: List<SubcatItem?>? = null
)

data class SubcatItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("weight")
	val weight: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
