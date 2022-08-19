package com.example.lawbeats.presentation.recycler

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.app_domain.entity.NewsEntity
import com.example.lawbeats.R
import com.example.lawbeats.databinding.FirstNewsItemBinding

class FirstNewsViewHolder(val binding: FirstNewsItemBinding) :
    NewsViewHolderInterface(binding.root) {
    override fun bindData(newsEntity: NewsEntity, onNewsSelected: (news: NewsEntity) -> Unit) {
        binding.newsHeading.setText(newsEntity.title)
        binding.authorAndDate.setText("${newsEntity.author} | ${newsEntity.time}")
        binding.root.setOnClickListener {
            onNewsSelected(newsEntity)
        }
        if (newsEntity.imgUrl != null) {
            Glide.with(binding.newsImage.context).load(newsEntity.imgUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.news_image)
                .into(binding.newsImage)
        } else {
            binding.newsImage.setImageResource(R.drawable.news_image)

        }
    }
}