package com.example.lawbeats.presentation.recycler

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeFragmentViewModel : ViewModel() {
    val selectedTab = MutableLiveData<String>()

}