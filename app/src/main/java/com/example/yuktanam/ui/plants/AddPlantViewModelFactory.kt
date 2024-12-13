package com.example.yuktanam.ui.plants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yuktanam.logic.data.repository.PlantRepository
import com.example.yuktanam.ui.plants.addplants.AddPlantViewModel

// ViewModel Factory
class AddPlantViewModelFactory(private val repository: PlantRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddPlantViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddPlantViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

