package com.example.demo_login_repo

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginEntity (
    @PrimaryKey
    val id:Int?=null,
    @ColumnInfo(name = "full_name")
    val fullName:String,
    @ColumnInfo(name = "mobile_no")
    val mobile_no:String,
    @ColumnInfo(name = "email")
    val email:String,
    @ColumnInfo(name = "state")
    val state:String,
    @ColumnInfo(name = "city")
    val city:String,
    @ColumnInfo(name = "gst")
    val gst:String,
    @ColumnInfo(name = "password")
    val password:String,
    @ColumnInfo(name = "confirmPassword")
    val confirmPassword:String
)