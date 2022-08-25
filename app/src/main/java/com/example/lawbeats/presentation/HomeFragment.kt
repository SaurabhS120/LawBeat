package com.example.lawbeats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.app_domain.entity.NewsTabEntity
import com.example.app_domain.repo.TabsRepo
import com.example.app_domain.state.NewsTabApiState
import com.example.lawbeats.databinding.FragmentHomeBinding
import com.example.lawbeats.presentation.paging_adapter.NewsCategoriesAdapter
import com.example.lawbeats.presentation.viewmodel.HomeFragmentViewModel
import com.example.lawbeats_retrofit_repo.repo.NewsTabsRetrofitRepoImpl
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    lateinit var homeFragmentViewModel: HomeFragmentViewModel
    companion object{
//        val tabsRepo:TabsRepo = DemoTabRepo()
        val tabsRepo:TabsRepo = NewsTabsRetrofitRepoImpl()
    }

    lateinit var binding: FragmentHomeBinding
    var tabList: List<NewsTabEntity>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeFragmentViewModel: HomeFragmentViewModel by activityViewModels()
        this.homeFragmentViewModel = homeFragmentViewModel
        homeFragmentViewModel.tabsResponse.observe(viewLifecycleOwner) { tabListResponse ->
            when (tabListResponse) {
                is NewsTabApiState.Success -> {
                    tabList = tabListResponse.tabList
                    binding.viewPager.adapter =
                        NewsCategoriesAdapter(this@HomeFragment, tabListResponse.tabList)
                    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                        tab.text = tabListResponse.tabList.get(position).name
                    }.attach()
                }
                is NewsTabApiState.Failure -> Toast.makeText(
                    requireContext(),
                    tabListResponse.errorMsg,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        homeFragmentViewModel.selectedTab.observe(viewLifecycleOwner) { selectedItemName ->
            tabList?.forEachIndexed { index, newsTabEntity ->
                if (newsTabEntity.name == selectedItemName) {
                    binding.viewPager.currentItem = index
                    return@observe
                }
            }
        }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                homeFragmentViewModel.selectedTab.postValue(tab?.text.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}