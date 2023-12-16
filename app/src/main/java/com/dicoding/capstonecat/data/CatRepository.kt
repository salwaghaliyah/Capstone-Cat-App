package com.dicoding.capstonecat.data

import android.net.Uri
import com.dicoding.capstonecat.data.entity.CatFavEntity
import com.dicoding.capstonecat.data.model.Cat
import com.dicoding.capstonecat.data.retrofit.ApiService
import com.dicoding.capstonecat.data.model.CatPredictionResult
import com.dicoding.capstonecat.data.response.CatResponseItem
import com.dicoding.capstonecat.data.room.CatDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CatRepository private constructor(
    private val apiService: ApiService,
    private val catDao: CatDao
){
    suspend fun getAllCatsFromApi(): List<Cat> {
        return try {
            apiService.getAllCats()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getCatByBreed(breed: String): CatResponseItem {
        val response = apiService.getCatByBreed(breed)
        return response.body() ?: throw CatNotFoundException("Cat not found for breed: $breed")
    }

    suspend fun getFavCats(): Flow<List<CatFavEntity>> = flowOf(catDao.getCats())

    suspend fun getFavCatByBreed(breed: String): Flow<Int> = flowOf(catDao.getFavCatByBreed(breed))

    suspend fun insertFavCat(cat: CatFavEntity): Flow<Boolean> {
        catDao.insertFavCat(cat)
        return flowOf(true)
    }

    suspend fun deleteFavCat(breed: String): Flow<Boolean> {
        catDao.deleteFavCat(breed)
        return flowOf(true)
    }



    suspend fun predictCatType(data: Uri?): CatPredictionResult {
        val response = apiService.predictCatType(data)
        return response.body() ?: throw CatPredictionFailedException("Failed to predict cat type")
    }

    companion object {
        @Volatile
        private var instance: CatRepository? = null

        fun getInstance(apiService: ApiService, catDao: CatDao): CatRepository =
            instance ?: synchronized(this) {
                instance ?: CatRepository(apiService, catDao)
            }.also { instance = it }
    }
}

class CatNotFoundException(message: String) : Exception(message)
class CatPredictionFailedException(message: String) : Exception(message)