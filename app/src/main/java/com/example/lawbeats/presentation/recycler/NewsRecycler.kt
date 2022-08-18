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

class NewsRecycler : ListAdapter<NewsEntity, NewsRecycler.NewsViewHolder>(NewsEntity.DIFF_UTIL) {
    inner class NewsViewHolder(val binding:NewsItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bindData(newsEntity: NewsEntity){
            binding.newsHeading.setText(newsEntity.title)
//            binding.newsContent.setText(newsEntity.content)
            if(newsEntity.imgUrl != null){
                newsEntity.imgUrl?.let {
                    binding.newsImage.setImageDrawable(null)
                    Glide.with(binding.newsImage.context).load(it).placeholder(R.drawable.news_image).into(binding.newsImage)
                    Log.d("img url",it)
                }
            }else{
                binding.newsImage.setImageResource(R.drawable.news_image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}