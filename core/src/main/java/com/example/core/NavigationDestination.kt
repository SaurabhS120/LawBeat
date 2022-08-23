package com.example.core

import com.example.app_domain.entity.NewsEntity

sealed class NavigationDestination {
    class LoginDestination() : NavigationDestination()
    class RegisterDestination() : NavigationDestination()
    class DetailedNewsDestination(val news: NewsEntity) : NavigationDestination()
    class SearchDestination() : NavigationDestination()
}
