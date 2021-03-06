package com.example.moviesapp.data.mapper

import com.example.moviesapp.data.local.model.LocalMovie
import com.example.moviesapp.data.remote.model.RemoteMovie
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeLocalMovie
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeLocalMovies
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeRemoteMovies
import io.kotest.matchers.shouldBe
import org.junit.Test

class DataMovieMapperTest {

    private val mapper = DataMovieMapper()

    @Test
    fun `given localMovie, when toDomain, get movie`() {
        val fakeLocalMovie = makeFakeLocalMovie()

        val movie = mapper.toDomain(fakeLocalMovie)

        assertAllFields(fakeLocalMovie, movie)
    }

    @Test
    fun `given localMovie's list, when toDomain, get movie's list`() {
        val fakeLocalMovies = makeFakeLocalMovies()

        val movies = mapper.toDomain(fakeLocalMovies)

        fakeLocalMovies.zip(movies)
            .forEach { (localMovie, movie) -> assertAllFields(localMovie, movie) }
    }

    private fun assertAllFields(localMovie: LocalMovie, movie: Movie) {
        localMovie.id shouldBe movie.id
        localMovie.title shouldBe movie.title
        localMovie.overview shouldBe movie.overview
        localMovie.isOnlyForAdults shouldBe movie.isOnlyForAdults
        localMovie.posterUrl shouldBe movie.posterUrl
        localMovie.voteAverage shouldBe movie.voteAverage
        localMovie.releaseDate shouldBe movie.releaseDate
    }

    @Test
    fun `given remoteMovie's list, when toLocal, get localMovie's list`() {
        val fakeRemoteMovies = makeFakeRemoteMovies(3)

        val localMovies = mapper.toLocal(fakeRemoteMovies)

        fakeRemoteMovies.zip(localMovies)
            .forEach { (remoteMovie, localMovie) -> assertAllFields(remoteMovie, localMovie) }
    }

    private fun assertAllFields(remoteMovie: RemoteMovie, localMovie: LocalMovie) {
        remoteMovie.id shouldBe localMovie.id
        remoteMovie.title shouldBe localMovie.title
        remoteMovie.overview shouldBe localMovie.overview
        remoteMovie.adult shouldBe localMovie.isOnlyForAdults
        remoteMovie.poster_path shouldBe localMovie.posterUrl
        remoteMovie.vote_average shouldBe localMovie.voteAverage
        remoteMovie.release_date shouldBe localMovie.releaseDate
    }
}