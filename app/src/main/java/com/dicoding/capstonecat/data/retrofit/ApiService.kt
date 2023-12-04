package com.dicoding.capstonecat.data.retrofit

import com.dicoding.capstonecat.data.model.CatModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("/api/images/deskripsi")
    suspend fun getCatInfo(@Query("breed") breed: String): Response<CatModel>

}
