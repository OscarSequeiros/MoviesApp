package com.example.moviesapp.presentation

import com.example.moviesapp.presentation.model.PresentationMovie

sealed class MoviesState {

    object IdleState : MoviesState()

    object LoadingState : MoviesState()

    object EmptyMoviesState : MoviesState()

    data class FilledMoviesState(val movies: List<PresentationMovie>) : MoviesState()
}