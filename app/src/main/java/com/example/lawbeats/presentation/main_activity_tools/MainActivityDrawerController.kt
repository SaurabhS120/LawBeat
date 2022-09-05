package com.example.lawbeats.presentation.main_activity_tools

import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lawbeats.R
import com.example.lawbeats.presentation.DrawerStates
import com.example.lawbeats.presentation.MainActivity
import com.example.lawbeats.presentation.viewmodel.HomeFragmentViewModel

class MainActivityDrawerController(
    private val activity: MainActivity,
    private val drawerLayout: DrawerLayout
) {

    private val openDrawerDrawable =
        ContextCompat.getDrawable(activity, R.drawable.ic_baseline_close_24)
    private val closeDrawerDrawable =
        ContextCompat.getDrawable(activity, R.drawable.ic_icon_feather_menu)

    private var drawerMenuItem: MenuItem? = null
    private lateinit var homeFragmentViewModel: HomeFragmentViewModel
    fun setDrawerMenuItem(drawerMenuItem: MenuItem) {
        this.drawerMenuItem = drawerMenuItem
    }

    fun initialize() {
        val homeFragmentViewModel:HomeFragmentViewModel by activity.viewModels()
        this.homeFragmentViewModel = homeFragmentViewModel
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {
                drawerMenuItem?.setIcon(openDrawerDrawable)
            }

            override fun onDrawerClosed(drawerView: View) {
                drawerMenuItem?.setIcon(closeDrawerDrawable)
            }

            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

    fun toggleDrawer() {
        if (drawerLayout.isOpen) {
            closeDrawer()
        } else {
            openDrawer()
        }
    }

    fun openDrawer() {
        drawerLayout.open()
    }

    fun closeDrawer() {
        drawerLayout.close()
    }

    fun setDrawerState(state: DrawerStates){
        homeFragmentViewModel.drawerState.postValue(state)
    }

}