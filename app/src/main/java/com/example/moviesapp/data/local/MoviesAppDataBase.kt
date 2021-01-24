package com.example.moviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesapp.data.local.model.LocalMovie

@Database(entities = [LocalMovie::class], version = 1)
abstract class MoviesAppDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}