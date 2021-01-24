package com.example.moviesapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalMovie(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "posterUrl") val String: String,
    @ColumnInfo(name = "isOnlyForAdults") val isOnlyForAdults: Boolean,
)