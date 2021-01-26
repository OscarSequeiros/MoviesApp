package com.example.moviesapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalMovie(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "isOnlyForAdults") val isOnlyForAdults: Boolean,
    @ColumnInfo(name = "posterUrl") val posterUrl: String?,
    @ColumnInfo(name = "voteAverage") val voteAverage: Double,
    @ColumnInfo(name = "releaseDate") val releaseDate: String,
)