package com.example.yuktanam.logic.data.response

import com.google.gson.annotations.SerializedName

data class DetectionResponse(

	@field:SerializedName("poin")
	val poin: String? = null,

	@field:SerializedName("deskripsi_penyakit")
	val deskripsiPenyakit: String? = null,

	@field:SerializedName("penanganan_penyakit")
	val penangananPenyakit: List<String?>? = null,

	@field:SerializedName("nama_tanaman")
	val namaTanaman: String? = null,

	@field:SerializedName("nama_penyakit")
	val namaPenyakit: String? = null,

	@field:SerializedName("pencegahan_penyakit")
	val pencegahanPenyakit: List<String?>? = null
)
