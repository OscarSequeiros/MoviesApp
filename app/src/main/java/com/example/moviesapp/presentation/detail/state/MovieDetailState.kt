package com.example.moviesapp.presentation.detail.state

import com.example.moviesapp.presentation.model.PresentationMovie

sealed class MovieDetailState {

    object IdleState : MovieDetailState()

    object LoadingState : MovieDetailState()

    data class SuccessfulState(val movie: PresentationMovie) : MovieDetailState()

    object FailureState : MovieDetailState()
}
