package com.example.lawbeats.presentation.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.app_domain.entity.NewsEntity
import com.example.lawbeats.databinding.FirstNewsItemBinding
import com.example.lawbeats.databinding.NewsItemBinding

class NewsPagingAdapter(val context: Context) :
    PagingDataAdapter<NewsEntity, NewsViewHolderInterface>(NewsEntity.DIFF_UTIL) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolderInterface {
        return if (viewType == 1) FirstNewsViewHolder(
            FirstNewsItemBinding.inflate(
                LayoutInflater.from(
                    context
                ), parent, false
            )
        )
        else NewsViewHolder(NewsItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 1
        else 2
    }

    override fun onBindViewHolder(holder: NewsViewHolderInterface, position: Int) {
        val item = getItem(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
        item?.let {
            holder.bindData(it)
        }
    }
}