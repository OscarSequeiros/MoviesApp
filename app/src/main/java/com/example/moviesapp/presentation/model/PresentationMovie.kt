package com.example.moviesapp.presentation.model

data class PresentationMovie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val isOnlyForAdults: Boolean,
    val voteAverage: String,
    val releaseDate: String,
)