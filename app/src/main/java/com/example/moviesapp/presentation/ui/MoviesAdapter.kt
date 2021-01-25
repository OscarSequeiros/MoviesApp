package com.example.moviesapp.presentation.ui

import com.example.moviesapp.presentation.model.PresentationMovie
import javax.inject.Inject

class MoviesAdapter @Inject constructor(
    private val clickAction: (movieId: Long) -> Unit
) {

    private var movies: List<PresentationMovie> = emptyList()
}