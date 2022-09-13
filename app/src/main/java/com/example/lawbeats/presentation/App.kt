package com.example.lawbeats.presentation

import android.app.Application
import com.example.app_domain.repo.MenuRepo
import com.example.lawbeats.data.shared_pref.repo.MenuSharedPrefRepo

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        setDarkOrLightMode()
    }
    private fun setDarkOrLightMode() {
        val repo: MenuRepo = MenuSharedPrefRepo(applicationContext)
        val isDarkMode = repo.isEnabled(LightNightMode.NIGHT_MODE_TITLE)
        LightNightMode.setDarkMode(isDarkMode)
    }
}