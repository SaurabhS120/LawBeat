package com.example.login.domain.repo

import android.content.Context
import com.example.login.domain.entity.RegistrationDetailsEntity
import com.example.login.domain.states.States

interface RegistrationRepo {
    fun invoke(applicationContext: Context,registrationDetailsEntity: RegistrationDetailsEntity):States
}