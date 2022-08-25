package com.example.lawbeats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import com.example.app_domain.entity.NewsTabEntity
import com.example.app_domain.repo.NewsDetailsLocalRepo
import com.example.app_domain.repo.TabsRepo
import com.example.app_domain.state.NewsTabApiState
import com.example.core.MainActivityInterface
import com.example.core.NavigationDestination
import com.example.lawbeats.R
import com.example.lawbeats.data.shared_pref.repo.SharedPrefNewsDetailsImpl
import com.example.lawbeats.databinding.ActivityMainBinding
import com.example.lawbeats.presentation.viewmodel.DetailedNewsViewModel
import com.example.lawbeats.presentation.viewmodel.HomeFragmentViewModel
import com.example.lawbeats_retrofit_repo.repo.NewsTabsRetrofitRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(),MainActivityInterface {
    lateinit var homeFragmentViewModel: HomeFragmentViewModel
    val tabsRepo: TabsRepo = NewsTabsRetrofitRepoImpl()
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var detailedNewsViewModel: DetailedNewsViewModel
    lateinit var newsDetailsLocalRepo: NewsDetailsLocalRepo
    var tabsList = listOf<NewsTabEntity>()
    lateinit var topMenu: Menu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsDetailsLocalRepo = SharedPrefNewsDetailsImpl(applicationContext)
        val detailedNewsViewModel: DetailedNewsViewModel by viewModels()
        val homeFragmentViewModel: HomeFragmentViewModel by viewModels()
        this.homeFragmentViewModel = homeFragmentViewModel
        this.detailedNewsViewModel = detailedNewsViewModel
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this), null, false)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
//        AppBarConfiguration(navController.graph, binding.drawerLayout)
//        binding.navView
//            .setupWithNavController(navController)
        setContentView(binding.root)
        setCategoriesInMenu()
        selectDrawerItem(R.id.home_fragment)
        binding.navView.setNavigationItemSelectedListener { selectedItem ->
            lifecycleScope.launch {
                when (selectedItem.itemId) {
                    R.id.home_fragment -> {
                        navigateTo(NavigationDestination.HomeDestination())
                        selectDrawerItem(R.id.home_fragment)
                    }
                    R.id.login_fragment -> {
                        navigateTo(NavigationDestination.LoginDestination())
                        selectDrawerItem(R.id.login_fragment)
                    }
                    else -> {
                        homeFragmentViewModel.selectedTab.postValue(selectedItem.title.toString())
                        navigateTo(NavigationDestination.HomeDestination())
                    }
                }
            }
            lifecycleScope.launch {
                binding.drawerLayout.close()
            }
            true
        }
        homeFragmentViewModel.selectedTab.observe(this) { selectedTabName ->
            selectDrawerItem(selectedTabName)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.drawerActionButton) {
            toggleDrawer()
            return true
        }
        if (item.itemId == R.id.searchActionButton) {
            navigateTo(NavigationDestination.SearchDestination())
        }
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
    override fun navigateTo(navigationDestination: NavigationDestination) {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_bar_menu, menu)
        topMenu = menu
        return super.onCreateOptionsMenu(menu)

    }

    fun setCategoriesInMenu() {
        lifecycleScope.launchWhenCreated {
            val tabs = tabsRepo.invoke()
            when (tabs) {
                is NewsTabApiState.Success -> {
                    tabsList = tabs.tabList
                    tabs.tabList.forEachIndexed { index, newsTabEntity ->
                        val id = View.generateViewId()
                        binding.navView.menu.add(Menu.NONE, id, index, newsTabEntity.name)
                    }
                }
                is NewsTabApiState.Failure -> {
                    Toast.makeText(this@MainActivity, tabs.errorMsg, Toast.LENGTH_SHORT).show()
                }
            }

        }
        binding.navView.menu.iterator().forEach { menuItem ->
            if (menuItem.itemId == R.id.home_fragment) menuItem.setChecked(true)
            else menuItem.setChecked(false)
        }
    }

    private fun selectDrawerItem(itemId: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.navView.menu.iterator().forEach { menuItem ->
                if (menuItem.itemId == itemId) menuItem.setChecked(true)
                else menuItem.setChecked(false)
            }
        }
    }

    private fun selectDrawerItem(tabName: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.navView.menu.iterator().forEach { menuItem ->
                if (menuItem.title == tabName) menuItem.setChecked(true)
                else menuItem.setChecked(false)
            }
        }
    }

    private fun toggleDrawer() {
        if (binding.drawerLayout.isOpen) {
            topMenu.findItem(R.id.drawerActionButton)
                .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_icon_feather_menu))
            binding.drawerLayout.close()

        } else {
            topMenu.findItem(R.id.drawerActionButton)
                .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_close_24))
            binding.drawerLayout.open()
        }
    }
}