package com.dicoding.capstonecat.data.response

import com.google.gson.annotations.SerializedName

data class CatResponse(

	@field:SerializedName("CatResponse")
	val catResponse: List<CatResponseItem?>? = null
)

data class CatResponseItem(

	@field:SerializedName("Perawatan")
	val perawatan: String? = null,

	@field:SerializedName("Kucing")
	val kucing: String? = null,

	@field:SerializedName("Ras")
	val ras: String? = null,

	@field:SerializedName("Makanan")
	val makanan: List<MakananItem?>? = null,

	@field:SerializedName("Vitamin")
	val vitamin: List<VitaminItem?>? = null,

	@field:SerializedName("Deskripsi")
	val deskripsi: String? = null
)

data class MakananItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null
)

data class VitaminItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null
)
