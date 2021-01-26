package com.example.moviesapp.presentation.detail.event

sealed class MovieDetailEvent {

    data class OpenMovieDetailsEvent(val movieId: Long): MovieDetailEvent()
}
