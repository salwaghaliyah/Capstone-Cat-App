package com.dicoding.capstonecat.data.retrofit

import com.dicoding.capstonecat.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor =
                if (BuildConfig.DEBUG){
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                } else {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
                }
            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://catstone-22122023.et.r.appspot.com/")   //sementara
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            
            return retrofit.create(ApiService::class.java)
        }

        fun getSecondApiService(): ApiService2 {
            val loggingInterceptor =
                if (BuildConfig.DEBUG){
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                } else {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
                }
            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://modelcats-408213.et.r.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService2::class.java)
        }
    }
}