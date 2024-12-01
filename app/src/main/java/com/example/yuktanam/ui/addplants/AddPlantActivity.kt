package com.example.yuktanam.ui.addplants

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yuktanam.MainActivity
import com.example.yuktanam.databinding.ActivityAddPlantBinding

class AddPlantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPlantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ButtonBack()
    }

    // Button Back Kembali ke Beranda
    private fun ButtonBack() {
        val btnBack = binding.btnBack
        btnBack.setOnClickListener{
            val intent = Intent(this@AddPlantActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}