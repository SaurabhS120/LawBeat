package com.example.lawbeats.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_domain.state.NewsTabApiState
import com.example.lawbeats.presentation.HomeFragment
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {
    val selectedTab = MutableLiveData<String>()
    val tabsResponse = MutableLiveData<NewsTabApiState>()

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