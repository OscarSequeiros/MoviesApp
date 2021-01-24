package com.example.moviesapp.presentation

import com.example.moviesapp.presentation.model.PresentationMovie

sealed class MoviesState {

    object LoadingState : MoviesState()

    object EmptyMoviesState : MoviesState()

    data class FillMoviesState(val movies: List<PresentationMovie>) : MoviesState()
}