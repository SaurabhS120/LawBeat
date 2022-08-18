package com.example.lawbeats.presentation.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.app_domain.entity.NewsEntity

abstract class NewsViewHolderInterface(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bindData(newsEntity: NewsEntity)
}