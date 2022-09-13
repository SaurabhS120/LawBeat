package com.example.lawbeats.presentation

import androidx.appcompat.app.AppCompatDelegate

object LightNightMode {
    val NIGHT_MODE_TITLE = "Night Mode"
    fun setDarkMode(isEnabled:Boolean){
        if(isEnabled){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}