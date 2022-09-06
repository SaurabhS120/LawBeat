package com.example.lawbeats.data.shared_pref.repo

import android.content.Context
import androidx.core.content.edit
import com.example.app_domain.entity.MenuItemEntity
import com.example.app_domain.repo.MenuRepo

class MenuSharedPrefRepo(applicationContext: Context):MenuRepo {
    val sp = applicationContext.getSharedPreferences("menu_checks",Context.MODE_PRIVATE)
    override fun saveIsChecked(menuItems: List<MenuItemEntity>) {
        sp.edit {
            menuItems.forEach {
                if (it.hasSwitch){
                    putBoolean(it.title,it.isChecked)
                }
            }
        }
    }

    override fun assignIsChecked(menuItems: List<MenuItemEntity>) {
        menuItems.forEach {
            if (it.hasSwitch){
                it.isChecked = sp.getBoolean(it.title,false)
            }
        }
    }
}