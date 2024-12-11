package com.example.yuktanam.logic.home.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yuktanam.R
import com.example.yuktanam.logic.database.addplant.PlantDatabase
import com.example.yuktanam.logic.database.addplant.PlantEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

//class PlantsAdapter(
//    private var plantList: List<Plant>,
//    private val onFavoriteClick: (Plant, Int) -> Unit
//) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {
//
//    var database: PlantDatabase? = null
//
//    // ViewHolder untuk dua kartu sekaligus (kiri dan kanan)
//    class PlantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        // Kartu kiri
//        val namePlant1: TextView = view.findViewById(R.id.name_plant)
//        val originPlant1: TextView = view.findViewById(R.id.origin_plant)
//        val imagePlant1: ImageView = view.findViewById(R.id.image_plant)
//        val favoritePlant1: ImageView = view.findViewById(R.id.favorite_plant)
//
//        // Kartu kanan
//        val namePlant2: TextView = view.findViewById(R.id.name_plant2)
//        val originPlant2: TextView = view.findViewById(R.id.origin_plant2)
//        val imagePlant2: ImageView = view.findViewById(R.id.image_plant2)
//        val favoritePlant2: ImageView = view.findViewById(R.id.favorite_plant2)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.plants_item, parent, false)
//        return PlantViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
//        // Set data untuk kartu kiri
//        val plant1 = plantList[position * 2]
//        holder.namePlant1.text = plant1.name
//        holder.originPlant1.text = plant1.origin
//        holder.imagePlant1.setImageResource(plant1.imageResource)
//        holder.favoritePlant1.setImageResource(
//            if (plant1.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite
//        )
//
//        // Listener untuk kartu kiri
////        holder.favoritePlant1.setOnClickListener {
////            plant1.isFavorite = !plant1.isFavorite // Toggle status favorit
////            onFavoriteClick(plant1, position * 2)
////            notifyItemChanged(position) // Update tampilan
////        }
//
//        holder.favoritePlant1.setOnClickListener {
//            val plant = plantList[position * 2]
//            plant.isFavorite = !plant.isFavorite
//            if (plant.isFavorite) {
//                saveToDatabase(plant)
//            } else {
//                deleteFromDatabase(plant)
//            }
//            notifyItemChanged(position)
//        }
//
//        // Set data untuk kartu kanan jika ada
//        if ((position * 2 + 1) < plantList.size) {
//            val plant2 = plantList[position * 2 + 1]
//            holder.namePlant2.text = plant2.name
//            holder.originPlant2.text = plant2.origin
//            holder.imagePlant2.setImageResource(plant2.imageResource)
//            holder.favoritePlant2.setImageResource(
//                if (plant2.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite
//            )
//
//            // Listener untuk kartu kanan
////            holder.favoritePlant2.setOnClickListener {
////                plant2.isFavorite = !plant2.isFavorite // Toggle status favorit
////                onFavoriteClick(plant2, position * 2 + 1)
////                notifyItemChanged(position) // Update tampilan
////            }
//            holder.favoritePlant2.setOnClickListener {
//                val index = position * 2 + 1
//                if (index < plantList.size) {
//                    val plant = plantList[index]
//                    plant.isFavorite = !plant.isFavorite
//                    if (plant.isFavorite) {
//                        saveToDatabase(plant)
//                    } else {
//                        deleteFromDatabase(plant)
//                    }
//                    notifyItemChanged(position)
//                }
//            }
//
//            // Pastikan kartu kanan terlihat
//            holder.namePlant2.visibility = View.VISIBLE
//            holder.originPlant2.visibility = View.VISIBLE
//            holder.imagePlant2.visibility = View.VISIBLE
//            holder.favoritePlant2.visibility = View.VISIBLE
//        } else {
//            // Jika tidak ada data untuk kartu kanan, sembunyikan kartu
//            holder.namePlant2.visibility = View.GONE
//            holder.originPlant2.visibility = View.GONE
//            holder.imagePlant2.visibility = View.GONE
//            holder.favoritePlant2.visibility = View.GONE
//        }
//    }
//
//    private fun saveToDatabase(plant: Plant) {
//        val plantEntity = PlantEntity(
//            id = UUID.randomUUID().toString(),
//            name = plant.name,
//            origin = plant.origin,
//            imageResource = plant.imageResource,
//            isFavorite = true
//        )
//        CoroutineScope(Dispatchers.IO).launch {
//            database?.plantDao()?.insertFavorite(plantEntity)
//        }
//    }
//
//    private fun deleteFromDatabase(plant: Plant) {
//        val plantEntity = PlantEntity(
//            id = UUID.randomUUID().toString(),
//            name = plant.name,
//            origin = plant.origin,
//            imageResource = plant.imageResource,
//            isFavorite = true
//        )
//        CoroutineScope(Dispatchers.IO).launch {
//            database?.plantDao()?.deleteFavorite(plantEntity)
//        }
//    }
//
//    fun updateData(newPlantList: List<Plant>) {
//        plantList = newPlantList
//        notifyDataSetChanged()
//    }
//
//    override fun getItemCount(): Int {
//        return (plantList.size + 1) / 2 // Setiap baris menampilkan 2 item
//    }
//}


