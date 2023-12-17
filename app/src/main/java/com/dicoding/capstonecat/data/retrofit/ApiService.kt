package com.dicoding.capstonecat.data.retrofit

import com.dicoding.capstonecat.data.model.Cat
import com.dicoding.capstonecat.data.response.CatResponseItem
import com.dicoding.capstonecat.data.response.ScanResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query


interface ApiService {

    @GET("api/data/all")
    suspend fun getAllCats(): List<Cat>

    @GET("api/data/kucing")
    suspend fun getCatByBreed(
        @Query("breed") breed: String
    ): Response<CatResponseItem>

}

interface ApiService2 {
    @Multipart
    @POST("prediction")
    suspend fun predictCatType(
        @Part file: MultipartBody.Part
    ): ScanResponse
}