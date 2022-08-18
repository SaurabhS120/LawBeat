package com.example.core

sealed class NavigationDestination{
    class LoginDestination(): NavigationDestination()
    class RegisterDestination(): NavigationDestination()
}
