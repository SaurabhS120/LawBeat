package com.example.app_domain.state

import com.example.app_domain.entity.NewsEntity

sealed class NewsApiState {
    class Success(val entities:List<NewsEntity>):NewsApiState()
    class Failure(val errorMsg:String):NewsApiState()
}