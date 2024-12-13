package com.example.yuktanam.ui.scan

import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.example.yuktanam.databinding.ActivityScanResultBinding
import org.json.JSONObject

class ScanResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari Intent
        val capturedImageUriString = intent.getStringExtra("capturedImageUri")
        val scanResultJson = intent.getStringExtra("scan_result")

        val scanResult = JSONObject(scanResultJson ?: "{}")
        val namaTanaman = scanResult.optString("nama_tanaman", "Tidak diketahui")
        val namaPenyakit = scanResult.optString("nama_penyakit", "Tidak diketahui")
        val deskripsiPenyakit = scanResult.optString("deskripsi_penyakit", "Tidak ada deskripsi")
        val poin = scanResult.optInt("poin", 0)

        // Set teks ke TextView
        binding.resultTextView.text = """
            Nama Tanaman: $namaTanaman
            Kondisi: $namaPenyakit
            Deskripsi: $deskripsiPenyakit
            Poin Kesehatan: $poin
        """.trimIndent()

        // Terapkan padding dan pusatkan teks
        binding.resultTextView.apply {
            setPadding(32, 16, 32, 16) // Margin internal di sisi kiri, atas, kanan, dan bawah
            gravity = Gravity.CENTER // Teks diatur agar terpusat
        }

        // Set gambar yang diambil
        capturedImageUriString?.let {
            val capturedImageUri = Uri.parse(it)
            binding.capturedImageView.setImageURI(capturedImageUri)
        }

        // Tombol kembali
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}