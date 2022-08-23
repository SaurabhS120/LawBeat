package com.example.app_domain.repo

import com.example.app_domain.state.NewsApiState

interface SearchNewsRepo {
    suspend fun invoke(keyword: String): NewsApiState
}