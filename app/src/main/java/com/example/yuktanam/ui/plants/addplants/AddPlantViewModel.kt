package com.example.yuktanam.ui.plants.addplants

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yuktanam.logic.data.repository.PlantRepository
import com.example.yuktanam.logic.data.response.AddPlantResponse
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddPlantViewModel(private val repository: PlantRepository) : ViewModel() {

    private val _addPlantResponse = MutableLiveData<AddPlantResponse>()
    val addPlantResponse: LiveData<AddPlantResponse> get() = _addPlantResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    val isLoading = MutableLiveData<Boolean>()

    fun addPlant(idUser: String, jenisTanaman: String, namaPanggilanTanaman: String, fotoUri: Uri, context: Context) {
        isLoading.value = true

        // Convert data to Multipart
        val idUserBody = idUser.toRequestBody("text/plain".toMediaTypeOrNull())
        val jenisTanamanBody = jenisTanaman.toRequestBody("text/plain".toMediaTypeOrNull())
        val namaPanggilanTanamanBody = namaPanggilanTanaman.toRequestBody("text/plain".toMediaTypeOrNull())

        val file = File(getRealPathFromURI(context, fotoUri))
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val fotoTanamanPart = MultipartBody.Part.createFormData("fotoTanaman", file.name, requestFile)

        viewModelScope.launch {
            try {
                val response = repository.addPlant(idUserBody, jenisTanamanBody, namaPanggilanTanamanBody, fotoTanamanPart)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _addPlantResponse.value = it
                    } ?: run {
                        _error.value = "Response body is null"
                    }
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Terjadi kesalahan: ${e.localizedMessage}"
            } finally {
                isLoading.value = false
            }
        }
    }

    private fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(contentUri, proj, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        val filePath = cursor?.getString(columnIndex ?: 0)
        cursor?.close()
        return filePath
    }
}






