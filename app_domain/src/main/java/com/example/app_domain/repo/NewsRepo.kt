package com.example.app_domain.repo

import com.example.app_domain.state.NewsApiState

interface NewsRepo {
    suspend fun invoke(uid:Int,page:Int,tid:Int,items_per_page:Int): NewsApiState
}