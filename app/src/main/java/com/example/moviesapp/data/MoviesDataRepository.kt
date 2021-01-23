package com.example.moviesapp.data

import com.example.moviesapp.data.local.MoviesLocalSource
import com.example.moviesapp.data.remote.MoviesRemoteSource
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.repository.MoviesRepository

class MoviesDataRepository(
        private val remoteSource: MoviesRemoteSource,
        private val localSource: MoviesLocalSource,
) : MoviesRepository {

    override fun getMovieById(id: Long): Movie {
        TODO("Not yet implemented")
    }

    override fun getPopularMovies(): List<Movie> {
        TODO("Not yet implemented")
    }
}