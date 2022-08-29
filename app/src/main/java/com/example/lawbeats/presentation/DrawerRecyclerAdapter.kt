package com.example.lawbeats.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.app_domain.entity.NewsTabEntity
import com.example.lawbeats.R
import com.example.lawbeats.databinding.DrawerItemBinding

class DrawerRecyclerAdapter(val onTabSelected: (tab: NewsTabEntity) -> Unit) :
    ListAdapter<NewsTabEntity, DrawerRecyclerAdapter.NewsDrawerViewHolder>(diffUtil) {
    private var selected_pos = 0

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<NewsTabEntity>() {
            override fun areItemsTheSame(oldItem: NewsTabEntity, newItem: NewsTabEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: NewsTabEntity,
                newItem: NewsTabEntity
            ): Boolean {
                return oldItem.equals(newItem)
            }

        }
    }

    inner class NewsDrawerViewHolder(val drawerItemBinding: DrawerItemBinding) :
        RecyclerView.ViewHolder(drawerItemBinding.root) {
        fun bind(newsTab: NewsTabEntity, position: Int) {
            drawerItemBinding.name.setText(newsTab.name)
            if (position == selected_pos) {
                drawerItemBinding.name.setTextColor(
                    ContextCompat.getColor(
                        drawerItemBinding.root.context,
                        R.color.colorPrimary
                    )
                )
            } else {
                drawerItemBinding.name.setTextColor(
                    ContextCompat.getColor(
                        drawerItemBinding.root.context,
                        R.color.defaultTextColor
                    )
                )
            }
        }

        fun setSelectedListener(position: Int) {
            drawerItemBinding.name.setOnClickListener {
                selectTab(position)
                onTabSelected(getItem(position))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsDrawerViewHolder {
        val binding = DrawerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = NewsDrawerViewHolder(binding)
        return holder
    }

    override fun onBindViewHolder(holder: NewsDrawerViewHolder, position: Int) {
        holder.bind(getItem(position), position)
        holder.setSelectedListener(position)
    }

    fun selectTab(position: Int) {
        val old_pos = selected_pos
        selected_pos = position
        notifyItemChanged(old_pos)
        notifyItemChanged(selected_pos)
    }

    fun selectTab(tabName: String?) {
        if (tabName == null) return
        val position: Int = currentList.indexOfFirst { it.name == tabName }
        selectTab(position)
    }
}