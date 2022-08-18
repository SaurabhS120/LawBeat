package com.example.login.data.demo.repo

import android.content.Context
import com.example.login.domain.entity.RegistrationDetailsEntity
import com.example.login.domain.repo.RegistrationRepo
import com.example.login.domain.states.States

class DemoRegistrationRepoImpl : RegistrationRepo{
    override fun invoke(
        applicationContext: Context,
        registrationDetailsEntity: RegistrationDetailsEntity
    ): States {
        return States.Success()
    }
}