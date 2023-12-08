package com.dicoding.capstonecat.data

import com.dicoding.capstonecat.data.model.Cat
import com.dicoding.capstonecat.data.retrofit.ApiService
import com.dicoding.capstonecat.data.model.CatModel

class CatRepository private constructor(
    private val apiService: ApiService
){

    companion object {
        @Volatile
        private var instance: CatRepository? = null

        fun getInstance(apiService: ApiService): CatRepository =
            instance ?: synchronized(this) {
                instance ?: CatRepository(apiService)
            }.also { instance = it }
    }

    suspend fun getCatDetail(breed: String): CatModel {
        return apiService.getCatInfo(breed).body() ?: throw Exception("Failed to fetch data")
    }

    suspend fun getAllCatsFromApi(): List<Cat> {
        return try {
            apiService.getAllCats()
        } catch (e: Exception) {
            emptyList()
        }
    }

}