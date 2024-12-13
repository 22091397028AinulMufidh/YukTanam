package com.example.yuktanam.ui.scan

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.yuktanam.R
import com.example.yuktanam.databinding.FragmentScanBinding
import com.example.yuktanam.ui.addplants.CameraActivity
import org.json.JSONObject

class ScanFragment : Fragment(R.layout.fragment_scan) {

    private lateinit var binding: FragmentScanBinding
    private var capturedImageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScanBinding.bind(view)

        // Tombol untuk mengambil gambar dari galeri
        binding.uploadImageButton.setOnClickListener {
            openGallery()
        }

        // Tombol untuk membuka kamera
        binding.cameraImageButton.setOnClickListener {
            openCameraX()
        }

        // Tombol untuk melakukan prediksi gambar
        binding.scanImageButton.setOnClickListener {
            scanImage()
        }
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                capturedImageUri = uri
                binding.previewImage.setImageURI(uri)
                processImage(uri)
            } else {
                Toast.makeText(requireContext(), "Gambar tidak dipilih", Toast.LENGTH_SHORT).show()
            }
        }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imagePath = result.data?.getStringExtra("CAMERAX_IMAGE_PATH")
                if (imagePath != null) {
                    capturedImageUri = imagePath.toUri()
                    binding.previewImage.setImageURI(capturedImageUri)
                } else {
                    Toast.makeText(requireContext(), "Gambar tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun openCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        cameraLauncher.launch(intent)
    }

    private fun processImage(uri: Uri) {
        val dummyResult = getDummyScanResult()

        val intent = Intent(requireContext(), ScanResultActivity::class.java).apply {
            putExtra("capturedImageUri", uri.toString())
            putExtra("scan_result", dummyResult)
        }
        startActivity(intent)
    }

    private fun scanImage() {
        if (capturedImageUri != null) {
            processImage(capturedImageUri!!)
        } else {
            Toast.makeText(requireContext(), "Harap unggah atau ambil gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDummyScanResult(): String {
        return """
            {
                "nama_tanaman": "Lidah Mertua",
                "nama_penyakit": "Sehat",
                "deskripsi_penyakit": "Tanaman dalam kondisi sehat, tidak menunjukkan gejala penyakit. Pertumbuhan normal, daun berwarna hijau segar, dan tidak ada tanda-tanda serangan hama atau penyakit.",
                "penanganan_penyakit": [],
                "pencegahan_penyakit": [
                    "Jaga kebersihan tanaman dan lingkungan sekitar.",
                    "Siram tanaman secara teratur, hindari penyiraman berlebihan.",
                    "Berikan pupuk yang sesuai dengan kebutuhan tanaman.",
                    "Pastikan tanaman mendapatkan cukup sinar matahari.",
                    "Lakukan pemeriksaan rutin terhadap tanaman untuk mendeteksi gejala penyakit atau serangan hama sedini mungkin."
                ],
                "poin": 95
            }
        """.trimIndent()
    }
}
