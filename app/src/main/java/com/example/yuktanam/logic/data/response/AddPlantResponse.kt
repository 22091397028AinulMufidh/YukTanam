package com.example.yuktanam.logic.data.response

data class AddPlantResponse(
	val data: Data? = null,
	val message: String? = null,
	val status: String? = null
)

data class Data(
	val jenisTanaman: String? = null,
	val createdAt: String? = null,
	val idNotification: String? = null,
	val namaPanggilanTanaman: String? = null,
	val id: String? = null,
	val idPlant: String? = null,
	val fotoTanaman: String? = null,
	val updatedAt: String? = null
)

