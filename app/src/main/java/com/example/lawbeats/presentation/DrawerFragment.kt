package com.example.lawbeats.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_domain.state.NewsTabApiState
import com.example.lawbeats.databinding.FragmentDrawerBinding
import com.example.lawbeats.presentation.recycler.DrawerRecyclerAdapter
import com.example.lawbeats.presentation.recycler.ExpandableListItem
import com.example.lawbeats.presentation.recycler.NewsTabsToExpandableMapper
import com.example.lawbeats.presentation.viewmodel.HomeFragmentViewModel

class DrawerFragment : Fragment() {
    lateinit var homeFragmentViewModel: HomeFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentDrawerBinding.inflate(layoutInflater, container, false)
        val homeFragmentViewModel: HomeFragmentViewModel by activityViewModels()
        this.homeFragmentViewModel = homeFragmentViewModel
        binding.drawerRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val adapter = DrawerRecyclerAdapter { newsTab ->
            when (newsTab) {
                is ExpandableListItem.CategoryExpandableListItem -> {
                    homeFragmentViewModel.selectedTab.postValue(newsTab.tab.name)
                    return@DrawerRecyclerAdapter true
                }
                is ExpandableListItem.SubCategoryExpandableListItem -> {
                    openNewsSubCategoryActivity(newsTab)
                    return@DrawerRecyclerAdapter false
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
        binding.settingsImageView.setOnClickListener {
            homeFragmentViewModel.drawerState.postValue(DrawerStates.MENU)
        }
        return binding.root
    }

    private fun openNewsSubCategoryActivity(newsTab: ExpandableListItem.SubCategoryExpandableListItem) {
        val subCatId: Int = newsTab.tab.id
        val categoryName: String = newsTab.tab.name
        val intent = Intent(requireActivity(), NewsListActivity::class.java).apply {
            putExtra("tab_id", subCatId)
            putExtra("category_name", categoryName)
        }
        startActivity(intent)
    }
}