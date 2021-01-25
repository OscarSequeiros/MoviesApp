package com.example.moviesapp.data.local

import com.example.moviesapp.data.local.model.LocalMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesLocalSource @Inject constructor(
    private val dataBase: MoviesAppDataBase
) {

    fun getAllMovies(): Flow<List<LocalMovie>> = dataBase.movieDao().getAllPopulars()

    suspend fun getMovieById(id: Long): LocalMovie = dataBase.movieDao().getById(id)

    suspend fun insertMovies(movies: List<LocalMovie>): Unit = dataBase.movieDao().insertAll(movies)
}