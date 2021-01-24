package com.example.moviesapp.data.mapper

import com.example.moviesapp.data.local.model.LocalMovie
import com.example.moviesapp.data.remote.model.RemoteMovie
import com.example.moviesapp.domain.model.Movie

class DataMovieMapper {

    fun toDomain(movies: List<LocalMovie>): List<Movie> {
        return movies.map { movie -> toDomain(movie) }
    }

    fun toDomain(movie: LocalMovie): Movie {
        return Movie(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterUrl = movie.posterUrl,
            isOnlyForAdults = movie.isOnlyForAdults
        )
    }

    fun toLocal(movies: List<RemoteMovie>): List<LocalMovie> {
        return movies.map { movie -> toDomain(movie) }
    }

    private fun toDomain(movie: RemoteMovie): LocalMovie {
        return LocalMovie(
            id = movie.id,
            title = movie.original_title,
            overview = movie.overview,
            posterUrl = movie.poster_path,
            isOnlyForAdults = movie.adult
        )
    }
}