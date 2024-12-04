package com.example.yuktanam.logic.database.addplant

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_plants")
data class PlantEntity(
    @PrimaryKey val id: String,
    val name: String,
    val origin: String,
    val imageResource: Int,
    val isFavorite: Boolean
)