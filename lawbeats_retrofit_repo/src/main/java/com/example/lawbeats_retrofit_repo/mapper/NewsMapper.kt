package com.example.lawbeats_retrofit_repo.mapper

import com.example.app_domain.entity.NewsEntity
import com.example.lawbeats_retrofit_repo.model.NewsResponse

internal object NewsMapper {
    fun convert(newsResponse: NewsResponse?): List<NewsEntity> {
        return newsResponse?.data?.map {
            NewsEntity(
                it?.title ?: "",
                it?.body ?: "",
                it?.image,
                it?.authorName ?: "",
                it?.date ?: "",
                it?.rawDate ?: "",
                it?.synopsis ?: "",
                it?.readingTime ?: ""
            )
        } ?: listOf()
    }
}