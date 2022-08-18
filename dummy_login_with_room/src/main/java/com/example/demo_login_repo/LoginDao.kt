package com.example.demo_login_repo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LoginDao {
    @Insert
    fun register(loginEntity: LoginEntity)

    @Query("SELECT COUNT(*)>0 FROM LoginEntity WHERE email = :email AND password = :password")
    fun login(email:String,password:String): Boolean
}