package com.example.moviesapp.factory

import com.example.moviesapp.data.local.model.LocalMovie
import com.example.moviesapp.data.remote.model.RemoteMovie
import com.example.moviesapp.factory.FakePrimitivesFactory.generateRandomDouble
import com.example.moviesapp.factory.FakePrimitivesFactory.generateRandomLong
import com.example.moviesapp.factory.FakePrimitivesFactory.generateRandomString
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

    fun makeFakeLocalMovie(): LocalMovie {
        return LocalMovie(
            id = generateRandomLong(),
            title = generateRandomString(),
            overview = generateRandomString(),
            posterUrl = generateRandomString(),
            isOnlyForAdults = false,
            voteAverage = generateRandomDouble(),
            releaseDate = generateRandomString(),
        )
    }

    fun makeFakeLocalMovies(count: Int = 4): List<LocalMovie> {
        return (0..count).map { makeFakeLocalMovie() }
    }

    private fun makeFakeRemoteMovie(): RemoteMovie {
        return RemoteMovie(
            id = generateRandomLong(),
            title = generateRandomString(),
            overview = generateRandomString(),
            poster_path = generateRandomString(),
            adult = false,
            vote_average = generateRandomDouble(),
            release_date = generateRandomString(),
        )
    }

    fun makeFakeRemoteMovies(count: Int = 4): List<RemoteMovie> {
        return (0..count).map { makeFakeRemoteMovie() }
    }
}