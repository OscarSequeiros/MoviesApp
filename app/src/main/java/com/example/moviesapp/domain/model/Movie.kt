package com.example.moviesapp.domain.model

data class Movie(
        val id: Long,
        val title: String,
        val overview: String,
        val posterUrl: String?,
        val isOnlyForAdults: Boolean,
        val voteAverage: Double,
        val releaseDate: String,
)