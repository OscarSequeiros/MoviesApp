package com.example.moviesapp.data.mapper

import com.example.moviesapp.data.local.model.LocalMovie
import com.example.moviesapp.data.remote.model.RemoteMovie
import com.example.moviesapp.domain.model.Movie
import javax.inject.Inject

class DataMovieMapper @Inject constructor() {

    fun toDomain(movies: List<LocalMovie>): List<Movie> {
        return movies.map { movie -> toDomain(movie) }
    }

    fun toDomain(movie: LocalMovie): Movie {
        return Movie(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterUrl = movie.posterUrl,
            isOnlyForAdults = movie.isOnlyForAdults,
            voteAverage = movie.voteAverage,
            releaseDate = movie.releaseDate,
        )
    }

    fun toLocal(movies: List<RemoteMovie>): List<LocalMovie> {
        return movies.map { movie -> toDomain(movie) }
    }

    private fun toDomain(movie: RemoteMovie): LocalMovie {
        return LocalMovie(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterUrl = movie.poster_path,
            isOnlyForAdults = movie.adult,
            voteAverage = movie.vote_average,
            releaseDate = movie.release_date,
        )
    }
}