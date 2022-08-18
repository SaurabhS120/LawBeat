package com.example.login.data.demo_db.repo

import android.content.Context
import com.example.demo_login_repo.LoginDbInterface
import com.example.login.domain.repo.LoginRepo
import com.example.login.domain.states.States

class DemoDbLoginRepo(applicationContext: Context):LoginRepo {
    val loginDao = LoginDbInterface(applicationContext)
    override fun invoke(email: String, password: String): States {
        if(loginDao.login(email,password)){
            return States.Success()
        }else{
            return States.Failure("Login failed")
        }
    }

}