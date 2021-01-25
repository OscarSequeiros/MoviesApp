package com.example.moviesapp.data.remote.model

data class RemoteMovie(
        val id: Long,
        val original_title: String,
        val overview: String,
        val poster_path: String,
        val adult: Boolean
)