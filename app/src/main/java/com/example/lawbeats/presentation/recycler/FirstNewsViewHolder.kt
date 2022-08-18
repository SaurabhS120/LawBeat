package com.example.lawbeats.presentation.recycler

import android.util.Log
import com.bumptech.glide.Glide
import com.example.app_domain.entity.NewsEntity
import com.example.lawbeats.R
import com.example.lawbeats.databinding.FirstNewsItemBinding

class FirstNewsViewHolder(val binding: FirstNewsItemBinding) :
    NewsViewHolderInterface(binding.root) {
    override fun bindData(newsEntity: NewsEntity) {
        binding.newsHeading.setText(newsEntity.title)
//            binding.newsContent.setText(newsEntity.content)
        if (newsEntity.imgUrl != null) {
            newsEntity.imgUrl?.let {
                binding.newsImage.setImageDrawable(null)
                Glide.with(binding.newsImage.context).load(it).placeholder(R.drawable.news_image)
                    .into(binding.newsImage)
                Log.d("img url", it)
            }
        } else {
            binding.newsImage.setImageResource(R.drawable.news_image)
        }
    }
}