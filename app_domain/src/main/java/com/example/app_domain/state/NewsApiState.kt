package com.example.app_domain.state

import com.example.app_domain.entity.NewsEntity

sealed class NewsApiState {
    class Success(val entities:List<NewsEntity>,val nextPage:Int?=null):NewsApiState()
    class Failure(val errorMsg:String):NewsApiState()
}