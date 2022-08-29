package com.example.lawbeats.presentation.main_activity_tools

import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.app_domain.repo.NewsDetailsLocalRepo
import com.example.core.NavigationDestination
import com.example.lawbeats.R
import com.example.lawbeats.presentation.MainActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

class MainActivityNavigationController(
    private val activity: MainActivity,
    private val navigationView: NavigationView,
    private val mainActivityDrawerController: MainActivityDrawerController,
    private val newsDetailsLocalRepo: NewsDetailsLocalRepo,
    private val navController: NavController
) {
    fun initialize() {
        navigationView.setNavigationItemSelectedListener { selectedItem ->
            activity.lifecycleScope.launch {
                when (selectedItem.itemId) {
                    R.id.home_fragment -> {
                        activity.navigateTo(NavigationDestination.HomeDestination())
//                        mainActivityDrawerController.selectDrawerItem(R.id.home_fragment)
                    }
                    R.id.login_fragment -> {
                        activity.navigateTo(NavigationDestination.LoginDestination())
//                        mainActivityDrawerController.selectDrawerItem(R.id.login_fragment)
                    }
                    else -> {
//                        activity.homeFragmentViewModel.selectedTab.postValue(selectedItem.title.toString())
                        activity.navigateTo(NavigationDestination.HomeDestination())
                    }
                }
            }
            activity.lifecycleScope.launch {
                mainActivityDrawerController.closeDrawer()
            }
            true
        }
    }

    fun navigateTo(navigationDestination: NavigationDestination) {
        when (navigationDestination) {
            is NavigationDestination.HomeDestination -> {
                navController.popBackStack(R.id.home_fragment, false)
            }

            is NavigationDestination.LoginDestination -> {
                navController.popBackStack(R.id.home_fragment, false)
                navController.navigate((R.id.login_fragment))
            }

            is NavigationDestination.RegisterDestination -> navController.navigate(R.id.registration_fragment)

            is NavigationDestination.DetailedNewsDestination -> {
                newsDetailsLocalRepo.saveNews(navigationDestination.news)
                navController.navigate(R.id.detailed_news_fragment)
            }

            is NavigationDestination.SearchDestination -> {
                navController.navigate(R.id.search_activity)
            }
        }
    }


}