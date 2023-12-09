package com.dicoding.capstonecat.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("Cats")
@Parcelize
class CatFavEntity (

    @ColumnInfo("breed")
    @PrimaryKey
    val breed: String = "",

    @ColumnInfo("imgUrl")
    val imgUrl: String? = null,

): Parcelable