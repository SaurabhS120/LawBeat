package com.example.lawbeats.presentation.recycler

import com.example.app_domain.entity.NewsSubCategoryEntity
import com.example.app_domain.entity.NewsTabEntity

sealed class ExpandableListItem {
    class CategoryExpandableListItem(
        val tab: NewsTabEntity,
        val isExpandable: Boolean,
        val expandableListItems: List<SubCategoryExpandableListItem>,
        var expanded: Boolean = false
    ) : ExpandableListItem()

    class SubCategoryExpandableListItem(
        val tab: NewsSubCategoryEntity,
        var isVisible: Boolean = false
    ) : ExpandableListItem()
}