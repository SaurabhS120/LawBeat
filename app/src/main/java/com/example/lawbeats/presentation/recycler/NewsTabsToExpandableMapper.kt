package com.example.lawbeats.presentation.recycler

import com.example.app_domain.entity.NewsTabEntity

object NewsTabsToExpandableMapper {
    fun convert(tabs: List<NewsTabEntity>): List<ExpandableListItem> {
        val expandableListData = mutableListOf<ExpandableListItem>()
        tabs.forEach {
            val isExpandable = it.newsCategories.isNullOrEmpty().not()
            val dropdown_items = it.newsCategories?.map {
                ExpandableListItem.SubCategoryExpandableListItem(it)
            } ?: listOf()
            val expandableListItem =
                ExpandableListItem.CategoryExpandableListItem(it, isExpandable, dropdown_items)
            expandableListData.add(expandableListItem)
            expandableListData.addAll(dropdown_items)
        }
        return expandableListData
    }
}