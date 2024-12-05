package com.example.yuktanam.logic.database.addplant

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlantDao {
    @Insert
    suspend fun insertFavorite(plant: PlantEntity)

    @Query("SELECT * FROM favorite_plants")
    fun getAllFavorites(): List<PlantEntity>

    @Delete
    suspend fun deleteFavorite(plant: PlantEntity)
}