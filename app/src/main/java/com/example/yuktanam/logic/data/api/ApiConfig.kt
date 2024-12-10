package com.example.yuktanam.logic.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private const val BASE_URL = "http://34.50.81.87:8080/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Menambahkan GsonConverterFactory
            .client(OkHttpClient()) // Jika perlu menggunakan OkHttp client
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
