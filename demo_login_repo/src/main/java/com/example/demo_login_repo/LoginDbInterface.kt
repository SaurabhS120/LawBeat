package com.example.demo_login_repo

import android.content.Context

class LoginDbInterface(applicationContext: Context) {
    private val dao = DemoLoginDatabaseSingleton.getInstance(applicationContext).loginDao()
    fun login(email:String,password:String): Boolean {
        return dao.login(email,password)
    }
    fun register(fullName:String,mobile_no:String,email:String,state:String,city:String,gst:String,password:String,confirmPassword:String):Boolean{
        val entity = LoginEntity(id = null,fullName,mobile_no,email,state,city,gst,password,confirmPassword)
        dao.register(entity)
        return true
    }
}