package com.example.app_domain.state

import com.example.app_domain.entity.NewsTabEntity

sealed class NewsTabApiState {
    class Success(val tabList:List<NewsTabEntity>):NewsTabApiState()
    class Failure(val errorMsg:String):NewsTabApiState()
}