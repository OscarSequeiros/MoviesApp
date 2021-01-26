package com.example.moviesapp.data.remote.model

data class RemoteMovie(
        val id: Long,
        val title: String,
        val overview: String,
        val poster_path: String,
        val adult: Boolean,
        val vote_average: Double,
        val release_date: String,
)