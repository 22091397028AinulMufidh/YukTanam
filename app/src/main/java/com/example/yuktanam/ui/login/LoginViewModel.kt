package com.example.yuktanam.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yuktanam.logic.data.api.ApiConfig
import com.example.yuktanam.logic.data.request.LoginRequest
import com.example.yuktanam.logic.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    fun loginUser(email: String, password: String, callback: (LoginResponse?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiConfig.apiService.loginUser(LoginRequest(email, password))

                if (response.isSuccessful) {
                    // Jika berhasil, kembalikan response body ke callback
                    callback(response.body())
                } else {
                    // Jika gagal, callback dengan null
                    callback(null)
                }
            } catch (e: Exception) {
                // Tangani exception, misalnya kesalahan jaringan
                Log.e("LoginError", "Error: ${e.localizedMessage}")
                callback(null)
            }
        }
    }
}

