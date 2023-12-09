package com.dicoding.capstonecat.data

import com.dicoding.capstonecat.data.entity.CatFavEntity
import com.dicoding.capstonecat.data.model.Cat
import com.dicoding.capstonecat.data.retrofit.ApiService
import com.dicoding.capstonecat.data.model.CatModel
import com.dicoding.capstonecat.data.response.CatResponseItem
import com.dicoding.capstonecat.data.room.CatDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CatRepository private constructor(
    private val apiService: ApiService,
    private val catDao: CatDao
){

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