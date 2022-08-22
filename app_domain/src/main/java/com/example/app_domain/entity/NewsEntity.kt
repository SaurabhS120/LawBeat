package com.example.app_domain.entity

import androidx.recyclerview.widget.DiffUtil

class NewsEntity(
    val title: String,
    val content: String,
    val imgUrl: String? = null,
    val author: String = "",
    val time: String = "",
    val raw_date: String = ""
) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<NewsEntity>() {
            override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                return newItem.title == oldItem.title
            }
        }
    }
}