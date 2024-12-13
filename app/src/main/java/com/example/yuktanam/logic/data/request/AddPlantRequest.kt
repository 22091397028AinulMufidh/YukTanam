package com.example.yuktanam.logic.data.request

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class AddPlantRequest(
    @SerializedName("jenisTanaman")
    val jenisTanaman: String,

    @SerializedName("namaPanggilanTanaman")
    val namaPanggilanTanaman: String,

    @SerializedName("fotoTanaman")
    val fotoTanaman: MultipartBody.Part
)
