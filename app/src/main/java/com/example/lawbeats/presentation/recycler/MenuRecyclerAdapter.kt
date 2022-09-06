package com.example.lawbeats.presentation.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lawbeats.databinding.MenuItemBinding
import com.example.lawbeats.presentation.MenuItemEntity

class MenuRecyclerAdapter(private val onDataChange:(List<MenuItemEntity>)->Unit) : RecyclerView.Adapter<MenuRecyclerAdapter.MenuItemViewHolder>() {
    val menuList = mutableListOf<MenuItemEntity>()
    inner class MenuItemViewHolder(private val menuItemBinding: MenuItemBinding):RecyclerView.ViewHolder(menuItemBinding.root){
        fun bind(itemEntity:MenuItemEntity){
            menuItemBinding.text.setText(itemEntity.title)
            menuItemBinding.switch1.visibility =
                if (itemEntity.hasSwitch){
                    menuItemBinding.switch1.isChecked = itemEntity.isChecked
                    View.VISIBLE
                }
                else View.INVISIBLE
            menuItemBinding.switch1.setOnCheckedChangeListener { compoundButton, b ->
                itemEntity.isChecked = b
                onDataChange(menuList)
            }
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
//        menuList = data.toMutableList()
//        notifyDataSetChanged()
        val oldSize = menuList.size
        menuList.clear()
        notifyItemRangeRemoved(0,oldSize)
        menuList.addAll(data)
        notifyItemRangeInserted(0,menuList.size)
    }
}