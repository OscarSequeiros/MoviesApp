package com.example.moviesapp

import com.example.moviesapp.FakePrimitivesFactory.generateRandomDouble
import com.example.moviesapp.FakePrimitivesFactory.generateRandomLong
import com.example.moviesapp.FakePrimitivesFactory.generateRandomString
import com.example.moviesapp.domain.model.Movie

object FakeMoviesFactory {

    fun makeFakeMovie(): Movie {
        return Movie(
            id = generateRandomLong(),
            title = generateRandomString(),
            overview = generateRandomString(),
            posterUrl = generateRandomString(),
            isOnlyForAdults = false,
            voteAverage = generateRandomDouble(),
            releaseDate = generateRandomString(),
        )
    }

    fun makeFakeMovies(count: Int = 4): List<Movie> {
        return (0..count).map { makeFakeMovie() }
    }
}