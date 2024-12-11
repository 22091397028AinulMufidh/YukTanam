package com.example.yuktanam.logic.home.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yuktanam.R
import com.example.yuktanam.databinding.PlantsItemBinding
import com.example.yuktanam.logic.data.response.PlantItem

class PlantAdapter(
    private var plantList: List<PlantItem>
) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    // Inner ViewHolder class
    inner class PlantViewHolder(private val binding: PlantsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plant: PlantItem) {
            // Bind data to the views
            binding.originPlant.text = plant.jenisTanaman ?: "Unknown Type"
            binding.namePlant.text = plant.namaPanggilanTanaman ?: "Unnamed Plant"

            // Load the image using Glide
            Glide.with(binding.imagePlant1.context)
                .load(plant.fotoTanaman)
                .placeholder(R.drawable.ic_image) // Add a placeholder image
                .error(R.drawable.eror_image) // Add an error image
                .into(binding.imagePlant1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val binding = PlantsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(plantList[position])
    }

    override fun getItemCount(): Int = plantList.size

    // Method to update the list dynamically
    fun updateData(newPlantList: List<PlantItem>) {
        plantList = newPlantList
        notifyDataSetChanged() // Notify adapter of the changes
    }
}

