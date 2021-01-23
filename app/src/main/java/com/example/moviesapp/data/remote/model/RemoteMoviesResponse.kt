package com.example.moviesapp.data.remote.model

data class RemoteMoviesResponse(
    val page: Int,
    val results: List<RemoteMovie>
)