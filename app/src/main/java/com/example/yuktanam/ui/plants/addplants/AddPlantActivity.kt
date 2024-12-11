package com.example.yuktanam.ui.plants.addplants

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.yuktanam.MainActivity
import com.example.yuktanam.databinding.ActivityAddPlantBinding
import com.example.yuktanam.logic.data.api.ApiConfig
import com.example.yuktanam.logic.data.repository.PlantRepository
import com.example.yuktanam.ui.plants.AddPlantViewModelFactory

class AddPlantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPlantBinding
    private lateinit var viewModel: AddPlantViewModel

    private var currentImageUri: Uri? = null

    // Untuk memeriksa izin kamera
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (!isGranted) {
                Toast.makeText(this, "Permintaan izin ditolak", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        val apiService = ApiConfig.apiService // Retrofit API Service
        val repository = PlantRepository(apiService)
        val factory = AddPlantViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[AddPlantViewModel::class.java]


        // Back button
        binding.btnBack.setOnClickListener { navigateToMain() }

        // Check camera permissions
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        // Gallery button
        binding.btnGaleri.setOnClickListener { startGallery() }

        // Add plant button
        binding.btnAddPlants.setOnClickListener { addPlant() }
    }


    private fun navigateToMain() {
        val intent = Intent(this@AddPlantActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.thumbnailImage.setImageURI(it)
        }
    }

    private fun addPlant() {
        val jenisTanaman = binding.textOriginName.text.toString()
        val namaPanggilanTanaman = binding.textCallName.text.toString()
        val fotoTanaman = currentImageUri

        if (jenisTanaman.isEmpty() || namaPanggilanTanaman.isEmpty() || fotoTanaman == null) {
            showToast("Harap lengkapi semua data!")
            return
        }

        // Ambil ID user dari SharedPreferences
        val sharedPreferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", null)

        if (userId == null) {
            showToast("Gagal autentikasi pengguna!")
            return
        }

        // Panggil fungsi viewModel.addPlant untuk mengirim data
        viewModel.addPlant(userId, jenisTanaman, namaPanggilanTanaman, fotoTanaman, this)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}



