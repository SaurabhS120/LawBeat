package com.example.login.domain.repo

import com.example.login.domain.states.States

interface LoginRepo {
    fun invoke(email:String,password:String):States
}