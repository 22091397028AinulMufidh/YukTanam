package com.example.yuktanam.logic.home.addplant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yuktanam.databinding.PlantsItemBinding

class PlantsAdapter(
    private val plantList: List<Plant>
) : RecyclerView.Adapter<PlantsAdapter.PlantViewHolder>() {

    // ViewHolder dengan View Binding
    class PlantViewHolder(private val binding: PlantsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plant: Plant) {
            binding.namePlant.text = plant.name
            binding.imagePlant.setImageResource(plant.imageResId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val binding = PlantsItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PlantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plantList[position]
        holder.bind(plant)
    }

    override fun getItemCount(): Int = plantList.size
}
