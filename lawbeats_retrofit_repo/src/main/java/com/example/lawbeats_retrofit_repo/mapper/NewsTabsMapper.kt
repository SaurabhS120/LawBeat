package com.example.lawbeats_retrofit_repo.mapper

import com.example.app_domain.entity.NewsSubCategoryEntity
import com.example.app_domain.entity.NewsTabEntity
import com.example.lawbeats_retrofit_repo.model.CatagoriesResponse

object NewsTabsMapper {
    internal fun convert(tabResponse: CatagoriesResponse?): List<NewsTabEntity> {
        return tabResponse?.data?.filterNotNull()?.map {
            NewsTabEntity(
                it.id.toString(),
                it.name ?: "none",
                it.subcat?.filterNotNull()?.map {
                    NewsSubCategoryEntity(it.id?.toInt() ?: 1, it.name ?: "none")
                }
            )
        } ?: listOf()
    }
}