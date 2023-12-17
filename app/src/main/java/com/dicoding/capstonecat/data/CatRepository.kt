package com.dicoding.capstonecat.data

import com.dicoding.capstonecat.data.entity.CatFavEntity
import com.dicoding.capstonecat.data.model.Cat
import com.dicoding.capstonecat.data.retrofit.ApiService
import com.dicoding.capstonecat.data.response.CatResponseItem
import com.dicoding.capstonecat.data.response.ScanResponse
import com.dicoding.capstonecat.data.retrofit.ApiService2
import com.dicoding.capstonecat.data.room.CatDao
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MultipartBody
import retrofit2.HttpException

class CatRepository private constructor(
    private val apiService: ApiService,
    private val apiService2: ApiService2,
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



    suspend fun predictCatType(data: MultipartBody.Part): ScanResponse {

        return try {
            val successResponse = apiService2.predictCatType(data)
            successResponse
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ScanResponse::class.java)
            errorResponse
            // emit(UiState.Error(errorResponse.status.toString()))
        }
//        val response = apiService.predictCatType(data)
//        return response.body() ?: throw CatPredictionFailedException("Failed to predict cat type")
    }

    companion object {
        @Volatile
        private var instance: CatRepository? = null

        fun getInstance(apiService: ApiService, apiService2: ApiService2, catDao: CatDao): CatRepository =
            instance ?: synchronized(this) {
                instance ?: CatRepository(apiService, apiService2, catDao)
            }.also { instance = it }
    }
}

class CatNotFoundException(message: String) : Exception(message)
class CatPredictionFailedException(message: String) : Exception(message)