package com.dicoding.capstonecat.di

import android.content.Context
import com.dicoding.capstonecat.data.CatRepository
import com.dicoding.capstonecat.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): CatRepository {
        val apiService = ApiConfig.getApiService()
        //val database = UserFavDatabase.getInstance(context)
       // val dao = database.userDao()
        //val appExecutors = Executor()

        return CatRepository.getInstance(apiService)
    }
}