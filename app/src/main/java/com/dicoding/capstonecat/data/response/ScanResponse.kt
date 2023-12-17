package com.dicoding.capstonecat.data.response

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: Status? = null
)

data class Data(

	@field:SerializedName("confidence")
	val confidence: Any? = null,

	@field:SerializedName("cat_types_prediction")
	val catTypesPrediction: String? = null
)

data class Status(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)
