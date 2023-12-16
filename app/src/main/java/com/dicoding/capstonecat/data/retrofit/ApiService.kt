package com.dicoding.capstonecat.data.retrofit

import android.net.Uri
import com.dicoding.capstonecat.data.model.Cat
import com.dicoding.capstonecat.data.model.CatModel
import com.dicoding.capstonecat.data.model.CatPredictionResult
import com.dicoding.capstonecat.data.response.CatResponseItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {

    @GET("api/data/all")
    suspend fun getAllCats(): List<Cat>

    @GET("api/data/kucing")
    suspend fun getCatByBreed(
        @Query("breed") breed: String
    ): Response<CatResponseItem>

    @POST("/prediction")
    suspend fun predictCatType(@Body data: Uri?): Response<CatPredictionResult>

}
