package com.example.lawbeats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.app_domain.repo.NewsDetailsLocalRepo
import com.example.core.MainActivityInterface
import com.example.core.NavigationDestination
import com.example.lawbeats.R
import com.example.lawbeats.data.shared_pref.repo.SharedPrefNewsDetailsImpl
import com.example.lawbeats.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(),MainActivityInterface {
    lateinit var binding:ActivityMainBinding
    lateinit var navController: NavController
    lateinit var detailedNewsViewModel: DetailedNewsViewModel
    lateinit var newsDetailsLocalRepo: NewsDetailsLocalRepo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsDetailsLocalRepo = SharedPrefNewsDetailsImpl(applicationContext)
        val detailedNewsViewModel: DetailedNewsViewModel by viewModels()
        this.detailedNewsViewModel = detailedNewsViewModel
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this), null, false)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.navView
            .setupWithNavController(navController)
        setContentView(binding.root)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.drawerActionButton) {
            if (binding.drawerLayout.isOpen) {
                binding.drawerLayout.close()
            } else {
                binding.drawerLayout.open()
            }
            return true
        }
        if (item.itemId == R.id.searchActionButton) {
            navigateTo(NavigationDestination.SearchDestination())
        }
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun navigateTo(navigationDestination: NavigationDestination) {
        when (navigationDestination) {

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
        return super.onCreateOptionsMenu(menu)

    }
}