package com.example.moviesapp.domain.usecase

import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.repository.MoviesRepository

class GetMovieById(private val repository: MoviesRepository) {

    suspend operator fun invoke(id: Long): Movie = repository.getMovieById(id)
}