package com.example.lawbeats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.lawbeats.data.demo.repo.DemoTabRepo
import com.example.lawbeats.databinding.FragmentHomeBinding
import com.example.app_domain.repo.TabsRepo
import com.example.app_domain.state.NewsTabApiState
import com.example.lawbeats_retrofit_repo.repo.NewsTabsRetrofitRepoImpl
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    companion object{
//        val tabsRepo:TabsRepo = DemoTabRepo()
        val tabsRepo:TabsRepo = NewsTabsRetrofitRepoImpl()
    }
    lateinit var binding:FragmentHomeBinding
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
        lifecycleScope.launchWhenStarted {
            val tabListResponse = tabsRepo.invoke()
            when(tabListResponse){
                is NewsTabApiState.Success->{
                    binding.viewPager.adapter = DemoCollectionAdapter(this@HomeFragment, tabListResponse.tabList)
                    TabLayoutMediator(binding.tabLayout,binding.viewPager){tab,position->
                        tab.text = tabListResponse.tabList.get(position).name
                    }.attach()
                }
                is NewsTabApiState.Failure-> Toast.makeText(requireContext(),tabListResponse.errorMsg,Toast.LENGTH_SHORT).show()
            }
        }
    }
}