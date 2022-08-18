package com.example.app_domain.repo

import com.example.app_domain.state.NewsApiState
import com.example.app_domain.state.NewsTabApiState

interface TabsRepo {
    suspend fun invoke():NewsTabApiState
}