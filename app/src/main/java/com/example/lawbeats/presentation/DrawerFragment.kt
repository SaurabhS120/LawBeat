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
import com.example.lawbeats.databinding.FragmentDrawerBinding
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
            homeFragmentViewModel.selectedTab.postValue(newsTab.name)
        }
        binding.drawerRecyclerView.adapter = adapter
        homeFragmentViewModel.tabsResponse.observe(viewLifecycleOwner) {
            val response = it
            when (response) {
                is NewsTabApiState.Success -> {
                    adapter.submitList(response.tabList)
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