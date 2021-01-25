package com.example.moviesapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapp.data.local.model.LocalMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM LocalMovie")
    fun getAllPopulars(): Flow<List<LocalMovie>>

    @Query("SELECT * FROM LocalMovie WHERE id = :id")
    suspend fun getById(id: Long): LocalMovie

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<LocalMovie>)
}