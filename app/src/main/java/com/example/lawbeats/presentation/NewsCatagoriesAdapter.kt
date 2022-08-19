package com.example.lawbeats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.app_domain.entity.NewsTabEntity

class NewsCatagoriesAdapter(fragment: Fragment, val tabList: List<NewsTabEntity>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = tabList.size

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val fragment = NewsFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt("tab_id", tabList.get(position).tid.toInt())
            putString("tab", tabList.get(position).name)
        }
        return fragment
    }
}