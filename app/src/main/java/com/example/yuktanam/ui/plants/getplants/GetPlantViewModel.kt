package com.example.yuktanam.ui.plants.getplants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yuktanam.logic.data.api.ApiConfig
import com.example.yuktanam.logic.data.response.GetPlantResponse
import com.example.yuktanam.logic.data.response.PlantItem
import kotlinx.coroutines.launch
import retrofit2.Response

class GetPlantViewModel : ViewModel() {

    private val _plants = MutableLiveData<List<PlantItem>>()
    val plants: LiveData<List<PlantItem>> get() = _plants

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchPlants() {
        viewModelScope.launch {
            try {
                val response: Response<GetPlantResponse> = ApiConfig.apiService.getPlants()
                if (response.isSuccessful) {
                    val rows = response.body()?.data?.rows ?: emptyList()
                    _plants.postValue(rows.filterNotNull())
                } else {
                    _errorMessage.postValue("Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Network Error: ${e.localizedMessage}")
            }
        }
    }
}
