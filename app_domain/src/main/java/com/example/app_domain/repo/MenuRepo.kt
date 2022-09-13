package com.example.app_domain.repo

import com.example.app_domain.entity.MenuItemEntity

interface MenuRepo {
    fun saveIsChecked(menuItems:List<MenuItemEntity>)
    fun assignIsChecked(menuItems:List<MenuItemEntity>)
    fun isEnabled(title: String): Boolean
}