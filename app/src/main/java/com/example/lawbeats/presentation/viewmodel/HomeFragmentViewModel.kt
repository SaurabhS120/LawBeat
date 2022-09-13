package com.example.lawbeats.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.app_domain.state.NewsTabApiState
import com.example.lawbeats.presentation.DrawerStates
import com.example.lawbeats.presentation.HomeFragment
import com.example.app_domain.entity.MenuItemEntity
import com.example.app_domain.repo.MenuRepo
import com.example.lawbeats.data.shared_pref.repo.MenuSharedPrefRepo
import com.example.lawbeats.presentation.LightNightMode
import kotlinx.coroutines.launch

class HomeFragmentViewModel(app:Application) : AndroidViewModel(app) {
    val selectedTab = MutableLiveData<String>()
    val tabsResponse = MutableLiveData<NewsTabApiState>()
    val drawerState = MutableLiveData(DrawerStates.DRAWER)
    val menu_data = listOf(
        MenuItemEntity("Notifications",true),
        MenuItemEntity(LightNightMode.NIGHT_MODE_TITLE,true),
        MenuItemEntity("Bookmarks"),
        MenuItemEntity("Share This App"),
        MenuItemEntity("About Us"),
        MenuItemEntity("Contact Us"),
        MenuItemEntity("Privacy Policy"),
        MenuItemEntity("Terms of use"),
    )
    val menu_repo:MenuRepo = MenuSharedPrefRepo(app)
    val menuItems = MutableLiveData(menu_data)
    init {
        menu_repo.assignIsChecked(menu_data)
        menuItems.postValue(menu_data)
        menuItems.observeForever {
            menu_repo.saveIsChecked(it)
        }
        getTabs()

    }

    fun getTabs() {
        viewModelScope.launch {
            val tabListResponse = HomeFragment.tabsRepo.invoke()
            tabsResponse.postValue(tabListResponse)
        }
    }

}