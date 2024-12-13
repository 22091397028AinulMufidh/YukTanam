package com.example.yuktanam.logic.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {

    private const val BASE_URL = "http://34.50.81.87:8080/"
    private const val TIME_OUT: Long = 30 // Waktu timeout dalam detik

    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .setLenient()
        .create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getOkHttpClient())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                readTimeout(TIME_OUT, TimeUnit.SECONDS)
                writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
            .build()
    }
}

