package com.example.app_domain.entity

class NewsTabEntity(
    val tid: String,
    val name: String,
    val newsCategories: List<NewsSubCategoryEntity>? = listOf()
)