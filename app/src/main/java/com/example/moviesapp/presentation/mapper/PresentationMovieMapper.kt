package com.example.moviesapp.presentation.mapper

import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.presentation.model.PresentationMovie

class PresentationMovieMapper {

    fun toPresentation(movies: List<Movie>): List<PresentationMovie> {
        return movies.map { movie -> toPresentation(movie) }
    }

    fun toPresentation(movie: Movie): PresentationMovie {
        return PresentationMovie(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterUrl = movie.posterUrl,
            isOnlyForAdults = movie.isOnlyForAdults
        )
    }
}