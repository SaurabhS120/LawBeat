package com.example.demo_login_repo

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LoginEntity::class], version = 1)
abstract class DemoLoginDatabase:RoomDatabase() {
    abstract fun loginDao():LoginDao
}