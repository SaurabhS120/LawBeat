package com.example.lawbeats.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_domain.state.NewsTabApiState
import com.example.lawbeats.presentation.DrawerStates
import com.example.lawbeats.presentation.HomeFragment
import com.example.lawbeats.presentation.MenuItemEntity
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {
    val selectedTab = MutableLiveData<String>()
    val tabsResponse = MutableLiveData<NewsTabApiState>()
    val drawerState = MutableLiveData(DrawerStates.DRAWER)
    val data = listOf(
        MenuItemEntity("Notifications",true),
        MenuItemEntity("Night Mode",true),
        MenuItemEntity("Bookmarks"),
        MenuItemEntity("Share This App"),
        MenuItemEntity("About Us"),
        MenuItemEntity("Contact Us"),
        MenuItemEntity("Privacy Policy"),
        MenuItemEntity("Terms of use"),
    )
    val menuItems = MutableLiveData(data)
    init {
        getTabs()
    }

    fun getTabs() {
        viewModelScope.launch {
            val tabListResponse = HomeFragment.tabsRepo.invoke()
            tabsResponse.postValue(tabListResponse)
        }
    }

}