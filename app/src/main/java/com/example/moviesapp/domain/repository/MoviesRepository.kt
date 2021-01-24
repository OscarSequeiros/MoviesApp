package com.example.moviesapp.domain.repository

import com.example.moviesapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMovieById(id: Long): Movie

    fun getPopularMovies(): Flow<List<Movie>>
}