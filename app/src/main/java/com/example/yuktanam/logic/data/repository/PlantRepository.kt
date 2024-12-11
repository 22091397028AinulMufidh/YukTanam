package com.example.yuktanam.logic.data.repository

import com.example.yuktanam.logic.data.api.ApiService
import com.example.yuktanam.logic.data.response.AddPlantResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class PlantRepository(private val apiService: ApiService) {
    suspend fun addPlant(
        idUser: RequestBody,
        jenisTanaman: RequestBody,
        namaPanggilanTanaman: RequestBody,
        fotoTanaman: MultipartBody.Part
    ): Response<AddPlantResponse> {
        return apiService.addPlant(idUser, jenisTanaman, namaPanggilanTanaman, fotoTanaman)
    }
}


