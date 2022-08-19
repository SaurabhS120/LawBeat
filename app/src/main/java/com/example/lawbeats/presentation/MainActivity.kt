package com.example.lawbeats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.core.MainActivityInterface
import com.example.core.NavigationDestination
import com.example.lawbeats.R
import com.example.lawbeats.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(),MainActivityInterface {
    lateinit var binding:ActivityMainBinding
    lateinit var navController: NavController
    lateinit var detailedNewsViewModel: DetailedNewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detailedNewsViewModel: DetailedNewsViewModel by viewModels()
        this.detailedNewsViewModel = detailedNewsViewModel
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this), null, false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.navView
            .setupWithNavController(navController)
        setContentView(binding.root)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            if (binding.drawerLayout.isOpen){
                binding.drawerLayout.close()
            }else{
                binding.drawerLayout.open()
            }
            return true
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
                detailedNewsViewModel.bindNews(navigationDestination.news)
                navController.navigate(R.id.detailed_news_fragment)
            }

        }
    }
}