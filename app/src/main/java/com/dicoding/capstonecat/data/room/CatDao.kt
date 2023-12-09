package com.dicoding.capstonecat.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.capstonecat.data.entity.CatFavEntity

@Dao
interface CatDao {
    @androidx.room.Query("SELECT * FROM cats")
    suspend fun getCats(): List<CatFavEntity>

    @androidx.room.Query("SELECT COUNT(*) FROM cats WHERE breed = :breed")
    suspend fun getFavCatByBreed(breed: String) : Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavCat(cat: CatFavEntity)

    @Query("DELETE FROM cats WHERE breed = :breed")
    suspend fun deleteFavCat(breed: String)
}