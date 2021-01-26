package com.example.moviesapp.factory

import com.example.moviesapp.data.local.model.LocalMovie

object FakeMovieFactory {

    fun makeFakeLocalMovies(count: Int = 4): List<LocalMovie> {
        return (0 until count).map { makeFakeLocalMovie() }
    }

    fun makeFakeLocalMovie(): LocalMovie {
        return LocalMovie(
            id = FakePrimitiveFactory.generateRandomLong(),
            title = FakePrimitiveFactory.generateRandomString(),
            overview = FakePrimitiveFactory.generateRandomString(),
            posterUrl = FakePrimitiveFactory.generateRandomString(),
            isOnlyForAdults = false,
            voteAverage = FakePrimitiveFactory.generateRandomDouble(),
            releaseDate = FakePrimitiveFactory.generateRandomString(),
        )
    }
}