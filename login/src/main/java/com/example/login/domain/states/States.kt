package com.example.login.domain.states

sealed class States {
    class Success():States()
    class Failure(val msg:String):States()

}