package com.example.login.data.demo_db.repo

import android.content.Context
import com.example.demo_login_repo.DemoLoginDatabaseSingleton
import com.example.demo_login_repo.LoginDbInterface
import com.example.login.domain.entity.RegistrationDetailsEntity
import com.example.login.domain.repo.LoginRepo
import com.example.login.domain.repo.RegistrationRepo
import com.example.login.domain.states.States

class DemoDbRegisterRepo():RegistrationRepo {
    lateinit var registerDao:LoginDbInterface
    override fun invoke(applicationContext: Context,registrationDetailsEntity: RegistrationDetailsEntity): States {
        if (this::registerDao.isInitialized.not()){
            registerDao = LoginDbInterface(applicationContext)
        }
        if(registerDao.register(registrationDetailsEntity.fullName,registrationDetailsEntity.mobile_no,registrationDetailsEntity.email,registrationDetailsEntity.state,registrationDetailsEntity.city,registrationDetailsEntity.gst,registrationDetailsEntity.password,registrationDetailsEntity.confirmPassword)){
            return States.Success()
        }else{
            return States.Failure("Registration failed")
        }
    }

}