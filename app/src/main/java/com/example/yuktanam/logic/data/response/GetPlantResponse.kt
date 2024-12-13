package com.example.yuktanam.logic.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetPlantResponse(

	@field:SerializedName("getplant")
	val data: GetPlant? = null
)

data class GetPlant(

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("rows")
	val rows: List<PlantItem?>? = null
)

@Parcelize
data class PlantItem(
	@field:SerializedName("jenisTanaman")
	val jenisTanaman: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("idNotification")
	val idNotification: String? = null,

	@field:SerializedName("namaPanggilanTanaman")
	val namaPanggilanTanaman: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("idPlant")
	val idPlant: String? = null,

	@field:SerializedName("fotoTanaman")
	val fotoTanaman: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable
