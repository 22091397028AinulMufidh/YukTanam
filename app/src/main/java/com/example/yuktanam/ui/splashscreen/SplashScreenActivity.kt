package com.example.yuktanam.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.yuktanam.MainActivity
import com.example.yuktanam.databinding.ActivitySplashScreenBinding
import com.example.yuktanam.ui.login.LoginActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // delay splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            val isLoggedIn = checkUserLoginStatus()
            val intent = if (isLoggedIn) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 2000)
    }

    private fun checkUserLoginStatus(): Boolean {
        // Logika untuk memeriksa apakah pengguna sudah login
        return false
    }
}
