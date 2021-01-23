package com.example.moviesapp.domain.model

data class Movie(
        val id: Long,
        val overview: String,
        val posterUrl: String,
        val title: String,
        val onlyForAdults: Boolean
)