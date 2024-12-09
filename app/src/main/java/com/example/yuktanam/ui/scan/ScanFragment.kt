package com.example.yuktanam.ui.scan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.yuktanam.R
import com.example.yuktanam.databinding.FragmentScanBinding
import com.example.yuktanam.ui.addplants.CameraActivity
import com.example.yuktanam.ui.addplants.CameraActivity.Companion.CAMERAX_RESULT

class ScanFragment : Fragment(R.layout.fragment_scan) {

    private lateinit var binding: FragmentScanBinding
    private var capturedImageUri: Uri? = null
    private lateinit var predictionHelper: PredictionHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentScanBinding.bind(view)

        binding.scanContainer.isEnabled = false

        predictionHelper = PredictionHelper(
            context = requireContext(),
            onResult = { result ->
//                binding.tvResult.text = result
            },
            onError = { errorMessage ->
                // Show error message using Toast
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            },
            onDownloadSuccess = {
                // Enable the scan container after download success
                binding.scanContainer.isEnabled = true
            }
        )

//        binding.scanContainer.setOnClickListener {
//            val input = binding.edSales.text.toString()
//            predictionHelper.predict(input)
//        }

        // Tombol Upload (Kiri): Buka galeri untuk memilih gambar
        binding.uploadImageButton.setOnClickListener {
            openGallery()
        }

        // Tombol Kamera (Kanan): Langsung buka CameraX
        binding.cameraImageButton.setOnClickListener {
            openCameraX()
        }

        // Tombol Scan (Tengah): Analisis gambar yang diambil atau diunggah
        binding.scanImageButton.setOnClickListener {
            scanImage()
        }
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                capturedImageUri = uri
                binding.previewImage.setImageURI(uri) // Menampilkan gambar dari galeri
            } else {
                Toast.makeText(requireContext(), "Gambar tidak dipilih", Toast.LENGTH_SHORT).show()
            }
        }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    // Buka CameraActivity dengan CameraX
    private fun openCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        cameraLauncher.launch(intent)
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == CAMERAX_RESULT) {
                val imagePath = result.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)
                if (imagePath != null) {
                    capturedImageUri = imagePath.toUri()
                    binding.previewImage.setImageURI(capturedImageUri) // Tampilkan gambar
                    Toast.makeText(requireContext(), "Gambar berhasil diambil", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Gambar tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun scanImage() {
        if (capturedImageUri != null) {
            // Lakukan sesuatu dengan gambar, seperti memulai ResultActivity
            val intent = Intent(requireContext(), ScanResultActivity::class.java)
            intent.putExtra("image_uri", capturedImageUri.toString())
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "Harap unggah atau ambil gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }
}
