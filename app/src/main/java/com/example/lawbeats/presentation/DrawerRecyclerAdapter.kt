package com.example.lawbeats.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lawbeats.ExpandableListItem
import com.example.lawbeats.R
import com.example.lawbeats.databinding.DrawerItemBinding
import com.example.lawbeats.databinding.DrawerItemExpandableBinding

class DrawerRecyclerAdapter(val onTabSelected: (tab: ExpandableListItem) -> Unit) :
    ListAdapter<ExpandableListItem, DrawerRecyclerAdapter.NewsDrawerViewHolder>(diffUtil) {
    private var selected_pos = 0
    private var expandedCategory: ExpandableListItem.CategoryExpandableListItem? = null

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ExpandableListItem>() {
            override fun areItemsTheSame(
                oldItem: ExpandableListItem,
                newItem: ExpandableListItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ExpandableListItem,
                newItem: ExpandableListItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    abstract class NewsDrawerViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {
        abstract fun bind(newsTab: ExpandableListItem, position: Int)

        abstract fun setSelectedListener(position: Int)

    }

    inner class NewsDrawerCatViewHolder(val drawerItemBinding: DrawerItemExpandableBinding) :
        NewsDrawerViewHolder(drawerItemBinding.root) {
        fun bind(newsTab: ExpandableListItem.CategoryExpandableListItem, position: Int) {
            drawerItemBinding.name.setText(newsTab.tab.name)
            if (position == selected_pos) {
                setTextColor(R.color.colorPrimary)
            } else {
                setTextColor(R.color.defaultTextColor)
            }
            if (newsTab.isExpandable) {
                showDropdown(newsTab)
            } else {
                hideDropdown()
            }
        }

        private fun hideDropdown() {
            drawerItemBinding.dropdownImage.visibility = View.GONE
        }

        private fun showDropdown(newsTab: ExpandableListItem.CategoryExpandableListItem) {
            drawerItemBinding.dropdownImage.visibility = View.VISIBLE
            setDropdownImageListener(newsTab)
        }

        private fun setTextColor(colorRes: Int) {
            drawerItemBinding.name.setTextColor(
                ContextCompat.getColor(
                    drawerItemBinding.root.context,
                    colorRes
                )
            )

        }

        private fun setDropdownImageListener(newsTab: ExpandableListItem.CategoryExpandableListItem) {
            if (newsTab.expanded) {
                expandedCategory = newsTab
                setCollapseListener(newsTab)
            } else {
                setExpandListener(newsTab)
            }
        }

        private fun collapse(newsTab: ExpandableListItem.CategoryExpandableListItem) {
            hideSubCategory(newsTab)
            setExpandListener(newsTab)
            collapseAlreadyExpanded()
            newsTab.expanded = false
            expandedCategory = null
        }

        private fun updateNewsItem(expandedCategory: ExpandableListItem.CategoryExpandableListItem) {
            val position = currentList.indexOfFirst { it == expandedCategory }
            notifyItemChanged(position)
        }

        private fun hideSubCategory(newsTab: ExpandableListItem.CategoryExpandableListItem) {
            newsTab.expandableListItems.forEach { subCat ->
                subCat.isVisible = false
                updateNewsItem(subCat)

            }
        }

        private fun updateNewsItem(subCat: ExpandableListItem.SubCategoryExpandableListItem) {
            val position: Int = currentList.indexOfFirst { it == subCat }
            notifyItemChanged(position)
        }

        private fun setExpandListener(newsTab: ExpandableListItem.CategoryExpandableListItem) {
            drawerItemBinding.dropdownImage.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
            drawerItemBinding.dropdownImage.setOnClickListener {
                expand(newsTab)
            }
        }

        fun collapseAlreadyExpanded() {
            expandedCategory?.let {
                hideSubCategory(it)
                it.expanded = false
                updateNewsItem(it)
            }

        }

        private fun expand(newsTab: ExpandableListItem.CategoryExpandableListItem) {
            showSubCategory(newsTab)
            setCollapseListener(newsTab)
            collapseAlreadyExpanded()
            expandedCategory = newsTab
            newsTab.expanded = true
        }

        private fun showSubCategory(newsTab: ExpandableListItem.CategoryExpandableListItem) {
            newsTab.expandableListItems.forEach { subCat ->
                subCat.isVisible = true
                updateNewsItem(subCat)
            }
        }

        private fun setCollapseListener(newsTab: ExpandableListItem.CategoryExpandableListItem) {
            drawerItemBinding.dropdownImage.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
            drawerItemBinding.dropdownImage.setOnClickListener {
                collapse(newsTab)
            }
        }

        override fun bind(newsTab: ExpandableListItem, position: Int) {
            when (newsTab) {
                is ExpandableListItem.CategoryExpandableListItem -> {
                    bind(newsTab, position)
                }
                is ExpandableListItem.SubCategoryExpandableListItem -> {
                    throw Exception("newsTab is not CategoryExpandableListItem")
                }
            }
        }

        override fun setSelectedListener(position: Int) {
            drawerItemBinding.name.setOnClickListener {
                selectTab(position)
                onTabSelected(getItem(position))
            }
        }

    }

    inner class NewsDrawerSubCatViewHolder(val drawerItemBinding: DrawerItemBinding) :
        NewsDrawerViewHolder(drawerItemBinding.root) {
        fun bind(newsTab: ExpandableListItem.SubCategoryExpandableListItem, position: Int) {
            drawerItemBinding.name.setText(newsTab.tab.name)
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
            if (newsTab.isVisible) {
                drawerItemBinding.root.visibility = View.VISIBLE
                drawerItemBinding.root.setLayoutParams(
                    RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                )
            } else {
                drawerItemBinding.root.visibility = View.GONE
                drawerItemBinding.root.setLayoutParams(RecyclerView.LayoutParams(0, 0))
            }
        }

        override fun bind(newsTab: ExpandableListItem, position: Int) {
            when (newsTab) {
                is ExpandableListItem.SubCategoryExpandableListItem -> {
                    bind(newsTab, position)
                }
                is ExpandableListItem.CategoryExpandableListItem -> {
                    throw Exception("newsTab is not CategoryExpandableListItem")
                }
            }
        }

        override fun setSelectedListener(position: Int) {
            drawerItemBinding.name.setOnClickListener {
                selectTab(position)
                onTabSelected(getItem(position))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsDrawerViewHolder {
        if (viewType == 2) {
            val binding =
                DrawerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            val holder = NewsDrawerSubCatViewHolder(binding)
            return holder
        } else {
            val binding = DrawerItemExpandableBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            val holder = NewsDrawerCatViewHolder(binding)
            return holder

        }
    }

    override fun onBindViewHolder(holder: NewsDrawerViewHolder, position: Int) {
        val item = getItem(position)

        when (item) {
            is ExpandableListItem.CategoryExpandableListItem -> {
                holder.bind(item, position)
                holder.setSelectedListener(position)
            }
            is ExpandableListItem.SubCategoryExpandableListItem -> {
                holder.bind(item, position)
                holder.setSelectedListener(position)
            }
            else -> {}
        }
    }

    fun selectTab(position: Int) {
        val old_pos = selected_pos
        selected_pos = position
        notifyItemChanged(old_pos)
        notifyItemChanged(selected_pos)
    }

    fun selectTab(tabName: String?) {
        if (tabName == null) return
        val position: Int = currentList.indexOfFirst {
            when (it) {
                is ExpandableListItem.CategoryExpandableListItem -> {
                    it.tab.name == tabName
                }
                else -> false
            }
        }
        selectTab(position)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item) {
            is ExpandableListItem.CategoryExpandableListItem -> 1
            is ExpandableListItem.SubCategoryExpandableListItem -> 2
        }

    }
}