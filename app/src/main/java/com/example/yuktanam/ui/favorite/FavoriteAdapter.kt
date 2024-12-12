package com.example.yuktanam.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yuktanam.databinding.FavoriteItemBinding

class FavoriteAdapter : ListAdapter<FavoriteItem, FavoriteAdapter.FavoriteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoriteViewHolder(private val binding: FavoriteItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavoriteItem) {
            binding.plantName.text = item.name
            binding.plantType.text = item.type
            binding.plantImage.setImageResource(item.imageResId)

            // Implement onClickListener for Detail button and heart icon if needed
            binding.buttonDetail.setOnClickListener {
                // Handle Detail button click
            }
            binding.heartIcon.setOnClickListener {
                // Handle favorite toggle
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FavoriteItem>() {
        override fun areItemsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem): Boolean {
            return oldItem == newItem
        }
    }
}