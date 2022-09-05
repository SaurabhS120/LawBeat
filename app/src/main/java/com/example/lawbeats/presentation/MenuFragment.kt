package com.example.lawbeats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lawbeats.R
import com.example.lawbeats.databinding.FragmentMenuBinding
import com.example.lawbeats.presentation.recycler.MenuRecyclerAdapter
import com.example.lawbeats.presentation.viewmodel.HomeFragmentViewModel

class MenuFragment : Fragment() {
    private lateinit var homeFragmentViewModel: HomeFragmentViewModel
    private lateinit var binding :FragmentMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(layoutInflater,container,false)
        initializeViewModels()
        // Inflate the layout for this fragment
        binding.backArrow.setOnClickListener {
            homeFragmentViewModel.drawerState.postValue(DrawerStates.DRAWER)
        }
        binding.menuRecycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        val adapter = MenuRecyclerAdapter()
        binding.menuRecycler.adapter = adapter
        adapter.setData(listOf(
            MenuItemEntity("Notifications",true),
            MenuItemEntity("Night Mode",true),
            MenuItemEntity("Bookmarks",false),
            MenuItemEntity("Share This App",false),
            MenuItemEntity("About Us",false),
            MenuItemEntity("Contact Us",false),
            MenuItemEntity("Privacy Policy",false),
            MenuItemEntity("Terms of use",false),
        ))
        return binding.root
    }

    private fun initializeViewModels() {
        val homeFragmentViewModel:HomeFragmentViewModel by activityViewModels()
        this.homeFragmentViewModel = homeFragmentViewModel
    }
}