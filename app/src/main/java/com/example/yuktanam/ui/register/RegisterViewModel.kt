package com.example.yuktanam.ui.register

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yuktanam.logic.data.api.ApiConfig
import com.example.yuktanam.logic.data.model.RegisterRequest
import com.example.yuktanam.logic.data.response.RegisterResponse
import com.example.yuktanam.ui.login.LoginActivity
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    // Fungsi untuk melakukan register
    fun registerUser(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String,
        context: Context, // Tambahkan context untuk navigasi
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val registerRequest = RegisterRequest(
                name = name,
                email = email,
                password = password,
                passwordConfirm = passwordConfirm
            )

            try {
                val response: Response<RegisterResponse> = ApiConfig.apiService.registerUser(registerRequest)
                if (response.isSuccessful) {
                    // Jika registrasi berhasil
                    onSuccess("Registrasi berhasil")

                    // Arahkan ke LoginActivity
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                } else {
                    onError("Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                // Tangani exception atau kesalahan jaringan
                Log.e("Registrasi gagal", "Error: ${e.localizedMessage}")
                onError("Network error: ${e.localizedMessage}")
            }
        }
    }

}