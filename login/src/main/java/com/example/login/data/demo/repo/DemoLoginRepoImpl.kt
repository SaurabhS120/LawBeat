package com.example.login.data.demo.repo

import com.example.login.domain.entity.RegistrationDetailsEntity
import com.example.login.domain.repo.LoginRepo
import com.example.login.domain.repo.RegistrationRepo
import com.example.login.domain.states.States

class DemoLoginRepoImpl : LoginRepo{
    override fun invoke(email: String, password: String): States {
        if (email == "test" && password == "test") return States.Success()
        else return States.Failure("Incorrect username or password")
    }
}