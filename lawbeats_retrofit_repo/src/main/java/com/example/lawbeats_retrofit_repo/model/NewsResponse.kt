package com.example.lawbeats_retrofit_repo.model

import com.google.gson.annotations.SerializedName

internal data class NewsResponse(

	@field:SerializedName("data")
	val data: List<DataItem1?>? = null,

	@field:SerializedName("pager")
	val pager: List<PagerItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

internal data class AuthorInfoItem(

	@field:SerializedName("author_name")
	val authorName: String? = null,

	@field:SerializedName("author_id")
	val authorId: String? = null
)

internal data class DataItem1(

	@field:SerializedName("author_name")
	val authorName: String? = null,

	@field:SerializedName("author_logo")
	val authorLogo: Any? = null,

	@field:SerializedName("reading_time")
	val readingTime: String? = null,

	@field:SerializedName("source_field")
	val sourceField: Any? = null,

	@field:SerializedName("category_name")
	val categoryName: String? = null,

	@field:SerializedName("view_node")
	val viewNode: String? = null,

	@field:SerializedName("nid")
	val nid: String? = null,

	@field:SerializedName("file_button_title")
	val fileButtonTitle: Any? = null,

	@field:SerializedName("other_images")
	val otherImages: Any? = null,

	@field:SerializedName("synopsis")
	val synopsis: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("Image")
	val image: String? = null,

	@field:SerializedName("Raw_Date")
	val rawDate: String? = null,

	@field:SerializedName("Date")
	val date: String? = null,

	@field:SerializedName("is_event")
	val isEvent: Int? = null,

	@field:SerializedName("bookmark")
	val bookmark: Int? = null,

	@field:SerializedName("pinstory")
	val pinstory: Int? = null,

	@field:SerializedName("premium")
	val premium: String? = null,

	@field:SerializedName("video_url")
	val videoUrl: Any? = null,

	@field:SerializedName("event_link")
	val eventLink: String? = null,

	@field:SerializedName("files")
	val files: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("author_info")
	val authorInfo: Any? = null
)

internal data class PagerItem(

	@field:SerializedName("items_per_page")
	val itemsPerPage: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("total_items")
	val totalItems: Int? = null,

	@field:SerializedName("current_page")
	val currentPage: Int? = null
)
