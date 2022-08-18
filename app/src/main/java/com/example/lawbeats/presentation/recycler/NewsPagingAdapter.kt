package com.example.lawbeats.presentation.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.app_domain.entity.NewsEntity
import com.example.lawbeats.databinding.NewsItemBinding

class NewsPagingAdapter(val context: Context) :
    PagingDataAdapter<NewsEntity, NewsViewHolder>(NewsEntity.DIFF_UTIL) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        return NewsViewHolder(NewsItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
        item?.let {
            holder.bindData(it)
        }
    }
}