package com.example.lawbeats.presentation.main_activity_tools

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.example.app_domain.entity.NewsTabEntity
import com.example.lawbeats.R
import com.example.lawbeats.presentation.MainActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityDrawerController(
    private val activity: MainActivity,
    private val drawerLayout: DrawerLayout,
    private val navigationView: NavigationView
) {

    private val openDrawerDrawable =
        ContextCompat.getDrawable(activity, R.drawable.ic_baseline_close_24)
    private val closeDrawerDrawable =
        ContextCompat.getDrawable(activity, R.drawable.ic_icon_feather_menu)

    private var drawerMenuItem: MenuItem? = null
    fun setDrawerMenuItem(drawerMenuItem: MenuItem) {
        this.drawerMenuItem = drawerMenuItem
    }

    fun toggleDrawer() {
        if (drawerLayout.isOpen) {
            closeDrawer()

        } else {
            openDrawer()
        }
    }

    fun openDrawer() {
        drawerMenuItem?.setIcon(openDrawerDrawable)
        drawerLayout.open()
    }

    fun closeDrawer() {
        drawerMenuItem?.setIcon(closeDrawerDrawable)
        drawerLayout.close()
    }

    fun selectDrawerItem(itemId: Int) {
        activity.lifecycleScope.launch(Dispatchers.Main) {
            navigationView.menu.iterator().forEach { menuItem ->
                if (menuItem.itemId == itemId) menuItem.setChecked(true)
                else menuItem.setChecked(false)
            }
        }
    }

    fun selectDrawerItem(tabName: String) {
        activity.lifecycleScope.launch(Dispatchers.Main) {
            navigationView.menu.iterator().forEach { menuItem ->
                if (menuItem.title == tabName) menuItem.setChecked(true)
                else menuItem.setChecked(false)
            }
        }
    }

    fun addNetworkTabs(tabList: List<NewsTabEntity>) {
        tabList.forEachIndexed { index, newsTabEntity ->
            val id = View.generateViewId()
            navigationView.menu.add(Menu.NONE, id, index, newsTabEntity.name)
        }
    }

}