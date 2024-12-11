package com.example.yuktanam.logic.data.api

import com.example.yuktanam.logic.data.request.LoginRequest
import com.example.yuktanam.logic.data.request.RegisterRequest
import com.example.yuktanam.logic.data.response.AddPlantResponse
import com.example.yuktanam.logic.data.response.GetPlantResponse
import com.example.yuktanam.logic.data.response.LoginResponse
import com.example.yuktanam.logic.data.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("api/v1/auth/register")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    @POST("api/v1/auth/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

//    @POST("api/v1/plants")
//    suspend fun addPlant(
//        @Body plantRequest: AddPlantRequest
//    ): Response<AddPlantResponse>

    @Multipart
    @POST("api/v1/plants")
    suspend fun addPlant(
        @Part("idUser") idUser: RequestBody,
        @Part("jenisTanaman") jenisTanaman: RequestBody,
        @Part("namaPanggilanTanaman") namaPanggilanTanaman: RequestBody,
        @Part fotoTanaman: MultipartBody.Part
    ): Response<AddPlantResponse>

    @GET("api/v1/plants")
    suspend fun getPlants(): Response<GetPlantResponse>
}
