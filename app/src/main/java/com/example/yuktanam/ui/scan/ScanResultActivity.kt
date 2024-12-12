package com.example.yuktanam.ui.scan

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.yuktanam.R

class ScanResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_result)

        val capturedImageView: ImageView = findViewById(R.id.capturedImageView)
        val resultTextView: TextView = findViewById(R.id.resultTextView)
        val backButton: ImageView = findViewById(R.id.backButton)

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
