package com.dicoding.capstonecat.di

import android.content.Context
import com.dicoding.capstonecat.data.CatRepository
import com.dicoding.capstonecat.data.retrofit.ApiConfig
import com.dicoding.capstonecat.data.room.CatsDatabase

object Injection {
    fun provideRepository(context: Context): CatRepository {
        val apiService = ApiConfig.getApiService()
        val database = CatsDatabase.getInstance(context)
        val catDao = database.catDao()

        return CatRepository.getInstance(apiService, catDao)
    }
}