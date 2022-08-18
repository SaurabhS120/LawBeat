package com.example.lawbeats.presentation.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lawbeats.databinding.NewsItemBinding
import com.example.app_domain.entity.NewsEntity
import com.example.lawbeats.R

class NewsRecycler : ListAdapter<NewsEntity, NewsViewHolder>(NewsEntity.DIFF_UTIL) {
    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}