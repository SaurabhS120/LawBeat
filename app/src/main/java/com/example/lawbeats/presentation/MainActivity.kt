package com.example.lawbeats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import com.example.core.MainActivityInterface
import com.example.core.NavigationDestination
import com.example.lawbeats.R
import com.example.lawbeats.data.shared_pref.repo.SharedPrefNewsDetailsImpl
import com.example.lawbeats.databinding.ActivityMainBinding
import com.example.lawbeats.presentation.main_activity_tools.MainActivityDrawerController
import com.example.lawbeats.presentation.main_activity_tools.MainActivityNavigationController
import com.example.lawbeats.presentation.viewmodel.DetailedNewsViewModel
import com.example.lawbeats.presentation.viewmodel.HomeFragmentViewModel


class MainActivity : AppCompatActivity(), MainActivityInterface {

    lateinit var homeFragmentViewModel: HomeFragmentViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var detailedNewsViewModel: DetailedNewsViewModel
    lateinit var drawerController: MainActivityDrawerController
    lateinit var navigationController: MainActivityNavigationController

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val newsDetailsLocalRepo = SharedPrefNewsDetailsImpl(applicationContext)
//        val tabsRepo: TabsRepo = NewsTabsRetrofitRepoImpl()

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(binding.root)

        setAppLayout()
        initializeViewModels()
        initializeFragmentNavigation()
        initializeNavigationController(newsDetailsLocalRepo)

    }

    private fun setAppLayout() {
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.appbar)
    }

    private fun initializeNavigationController(
        newsDetailsLocalRepo: SharedPrefNewsDetailsImpl
    ) {
        navigationController = MainActivityNavigationController(
            this,
            binding.navView,
            drawerController,
            newsDetailsLocalRepo,
            navController
        )
        navigationController.initialize()
        homeFragmentViewModel.selectedTab.observe(this) { selectedTabName ->
//            drawerController.selectDrawerItem(selectedTabName)
        }
    }

    private fun initializeFragmentNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
//        AppBarConfiguration(navController.graph, binding.drawerLayout)
//        binding.navView
//            .setupWithNavController(navController)
        drawerController =
            MainActivityDrawerController(this, binding.drawerLayout)
    }

    private fun initializeViewModels() {

        val detailedNewsViewModel: DetailedNewsViewModel by viewModels()
        val homeFragmentViewModel: HomeFragmentViewModel by viewModels()

        this.homeFragmentViewModel = homeFragmentViewModel
        this.detailedNewsViewModel = detailedNewsViewModel

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.drawerActionButton) {
            drawerController.toggleDrawer()
            return true
        }

        if (item.itemId == R.id.searchActionButton) {
            navigateTo(NavigationDestination.SearchDestination())
            return true
        }

        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun navigateTo(navigationDestination: NavigationDestination) {
        navigationController.navigateTo(navigationDestination)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.top_bar_menu, menu)

        val drawerMenuItem = menu.findItem(R.id.drawerActionButton)
        drawerController.setDrawerMenuItem(drawerMenuItem)

        return super.onCreateOptionsMenu(menu)

    }

}