package com.dicoding.capstonecat.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.capstonecat.data.entity.CatFavEntity

@Database(entities = [CatFavEntity::class], version = 1)
abstract class CatsDatabase: RoomDatabase() {

    abstract fun catDao(): CatDao

    companion object {
        @Volatile
        private var INSTANCE: CatsDatabase? = null

        fun getInstance(context: Context): CatsDatabase {
            INSTANCE ?: synchronized(CatsDatabase::class.java) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    CatsDatabase::class.java, "cat_database"
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE as CatsDatabase
        }
    }
}