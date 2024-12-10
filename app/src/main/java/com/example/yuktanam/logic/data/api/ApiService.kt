package com.example.yuktanam.logic.data.api

import com.example.yuktanam.logic.data.request.LoginRequest
import com.example.yuktanam.logic.data.request.RegisterRequest
import com.example.yuktanam.logic.data.response.LoginResponse
import com.example.yuktanam.logic.data.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/v1/auth/register")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    @POST("api/v1/auth/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
}
