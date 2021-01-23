package com.example.moviesapp.domain.repository

import com.example.moviesapp.domain.model.Movie

interface MoviesRepository {

    fun getMovieById(id: Long): Movie

    fun getPopularMovies(): List<Movie>
}