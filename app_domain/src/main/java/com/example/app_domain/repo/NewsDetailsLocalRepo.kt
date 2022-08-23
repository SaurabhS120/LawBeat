package com.example.app_domain.repo

import com.example.app_domain.entity.NewsEntity

interface NewsDetailsLocalRepo {
    fun saveNews(news: NewsEntity)
    fun getNews(): NewsEntity
}