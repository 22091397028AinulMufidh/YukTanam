package com.example.yuktanam.ui.scan

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.yuktanam.R
import com.example.yuktanam.databinding.ActivityScanResultBinding

class ScanResultActivity : AppCompatActivity() {

    private lateinit var predictionHelper: PredictionHelper
    private lateinit var binding: ActivityScanResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val capturedImageView: ImageView = findViewById(R.id.capturedImageView)
        val resultTextView: TextView = findViewById(R.id.resultTextView)
        val backButton: ImageView = findViewById(R.id.backButton)

        // Ambil data yang dikirim dari ScanFragment
        val result = intent.getStringExtra("scan_result") ?: "Hasil tidak ditemukan"

        // Atur teks ke TextView menggunakan View Binding
        binding.resultTextView.text = result

        val capturedImageUriString = intent.getStringExtra("capturedImageUri")
        val capturedImageUri = capturedImageUriString?.let { Uri.parse(it) }
        val scanResult = intent.getStringExtra("scanResult")

        capturedImageUri?.let {
            capturedImageView.setImageURI(it)
        }

        resultTextView.text = "Kondisi tanaman: ${scanResult ?: "Tidak diketahui"}"

        backButton.setOnClickListener {
            finish()
        }



    }
}

