package com.dicoding.capstonecat.data

import com.dicoding.capstonecat.data.retrofit.ApiService

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
}