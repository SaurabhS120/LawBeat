package com.example.demo_login_repo

import android.content.Context
import androidx.room.Room

object DemoLoginDatabaseSingleton {
    private lateinit var db:DemoLoginDatabase
    fun getInstance(applicationContext:Context): DemoLoginDatabase {
        return if (this::db.isInitialized)db
        else{
            db = Room.databaseBuilder(
                applicationContext,
                DemoLoginDatabase::class.java, "demo_login"
            ).build()
            db
        }
    }
}