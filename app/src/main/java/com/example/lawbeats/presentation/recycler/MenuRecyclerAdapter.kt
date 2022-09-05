package com.example.lawbeats.presentation.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lawbeats.databinding.MenuItemBinding
import com.example.lawbeats.presentation.MenuItemEntity

class MenuRecyclerAdapter : RecyclerView.Adapter<MenuRecyclerAdapter.MenuItemViewHolder>() {
    val menuList = mutableListOf<MenuItemEntity>()
    class MenuItemViewHolder(private val menuItemBinding: MenuItemBinding):RecyclerView.ViewHolder(menuItemBinding.root){
        fun bind(itemEntity:MenuItemEntity){
            menuItemBinding.text.setText(itemEntity.title)
            menuItemBinding.switch1.visibility =
                if (itemEntity.hasSwitch) View.VISIBLE
                else View.INVISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        holder.bind(menuList[position])
    }

    override fun getItemCount(): Int {
        return menuList.size
    }
    fun setData(data:List<MenuItemEntity>){
        val oldSize = menuList.size
        menuList.clear()
        notifyItemRangeRemoved(0,oldSize)
        menuList.addAll(data)
        notifyItemRangeInserted(0,menuList.size)
    }
}