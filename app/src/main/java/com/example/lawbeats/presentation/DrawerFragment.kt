package com.example.lawbeats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_domain.state.NewsTabApiState
import com.example.lawbeats.ExpandableListItem
import com.example.lawbeats.NewsTabsToExpandableMapper
import com.example.lawbeats.databinding.FragmentDrawerBinding
import com.example.lawbeats.presentation.recycler.DrawerRecyclerAdapter
import com.example.lawbeats.presentation.viewmodel.HomeFragmentViewModel

class DrawerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentDrawerBinding.inflate(layoutInflater, container, false)
        val homeFragmentViewModel: HomeFragmentViewModel by activityViewModels()
        binding.drawerRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val adapter = DrawerRecyclerAdapter { newsTab ->
            when (newsTab) {
                is ExpandableListItem.CategoryExpandableListItem -> {
                    homeFragmentViewModel.selectedTab.postValue(newsTab.tab.name)
                }
                is ExpandableListItem.SubCategoryExpandableListItem -> {
                    homeFragmentViewModel.selectedTab.postValue(newsTab.tab.name)
                }
            }
        }
        binding.drawerRecyclerView.adapter = adapter
        homeFragmentViewModel.tabsResponse.observe(viewLifecycleOwner) {
            val response = it
            when (response) {
                is NewsTabApiState.Success -> {
//                    val newsTabList = mutableListOf<ExpandableListItem>()
//                        response.tabList.forEach { newsTabEntity ->
//                        newsTabList.add(newsTabEntity)
//                            newsTabEntity.newsCategories?.forEach {
//                                newsTabList.add(NewsTabEntity("0",it.name))
//                            }
//                    }
                    val newsTabList = NewsTabsToExpandableMapper.convert(response.tabList)
                    adapter.submitList(newsTabList)
                }
                is NewsTabApiState.Failure -> {
                    Toast.makeText(requireContext(), response.errorMsg, Toast.LENGTH_SHORT).show()
                }
            }
        }
        homeFragmentViewModel.selectedTab.observe(viewLifecycleOwner) {
            adapter.selectTab(it)
        }
        return binding.root
    }
}