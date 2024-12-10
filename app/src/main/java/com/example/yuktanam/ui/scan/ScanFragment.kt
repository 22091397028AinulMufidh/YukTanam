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

class ScanFragment : Fragment(R.layout.fragment_scan) {

    private lateinit var binding: FragmentScanBinding
    private var capturedImageUri: Uri? = null
    private lateinit var predictionHelper: PredictionHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScanBinding.bind(view)

        // Nonaktifkan tombol scan sampai model siap
        binding.scanContainer.isEnabled = false

        // Inisialisasi PredictionHelper
        predictionHelper = PredictionHelper(
            context = requireContext(),
            onResult = { result ->
                // Setelah mendapatkan hasil, kirim ke ScanResultActivity
                val intent = Intent(requireContext(), ScanResultActivity::class.java)
                intent.putExtra("scan_result", result)
                startActivity(intent)
            },
            onError = { errorMessage ->
                // Menampilkan pesan error
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            },
            onDownloadSuccess = {
                // Enable tombol scan setelah model berhasil diunduh
                binding.scanContainer.isEnabled = true
            }
        )

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

    // Menggunakan ActivityResult API untuk mengambil gambar dari galeri
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                capturedImageUri = uri
                binding.previewImage.setImageURI(uri)
                // Prediksi langsung setelah gambar diambil
                processImage(uri)
            } else {
                Toast.makeText(requireContext(), "Gambar tidak dipilih", Toast.LENGTH_SHORT).show()
            }
        }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    // Menggunakan ActivityResult API untuk menangkap gambar dari kamera
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imagePath = result.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)
                if (imagePath != null) {
                    capturedImageUri = imagePath.toUri()
                    binding.previewImage.setImageURI(capturedImageUri)
                    Toast.makeText(requireContext(), "Gambar berhasil diambil", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Gambar tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun openCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        cameraLauncher.launch(intent)
    }

    // Fungsi untuk melakukan prediksi gambar
    private fun processImage(uri: Uri) {
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            predictionHelper.predict(bitmap) { result ->
                // Kirim hasil prediksi ke ScanResultActivity
                val intent = Intent(requireContext(), ScanResultActivity::class.java)
                intent.putExtra("scan_result", result)
                startActivity(intent)
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Gagal memproses gambar: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi untuk memulai analisis gambar
    private fun scanImage() {
        if (capturedImageUri != null) {
            processImage(capturedImageUri!!)
        } else {
            Toast.makeText(requireContext(), "Harap unggah atau ambil gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }
}


