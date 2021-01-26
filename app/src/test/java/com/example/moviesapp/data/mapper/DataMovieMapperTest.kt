package com.example.moviesapp.data.mapper

import com.example.moviesapp.data.local.model.LocalMovie
import com.example.moviesapp.data.remote.model.RemoteMovie
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeLocalMovie
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeLocalMovies
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeRemoteMovies
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
        assert(localMovie.id == movie.id)
        assert(localMovie.title == movie.title)
        assert(localMovie.overview == movie.overview)
        assert(localMovie.isOnlyForAdults == movie.isOnlyForAdults)
        assert(localMovie.posterUrl == movie.posterUrl)
        assert(localMovie.voteAverage == movie.voteAverage)
        assert(localMovie.releaseDate == movie.releaseDate)
    }

    @Test
    fun `given remoteMovie's list, when toLocal, get localMovie's list`() {
        val fakeRemoteMovies = makeFakeRemoteMovies(3)

        val localMovies = mapper.toLocal(fakeRemoteMovies)

        fakeRemoteMovies.zip(localMovies)
            .forEach { (remoteMovie, localMovie) -> assertAllFields(remoteMovie, localMovie) }
    }

    private fun assertAllFields(remoteMovie: RemoteMovie, localMovie: LocalMovie) {
        assert(remoteMovie.id == localMovie.id)
        assert(remoteMovie.title == localMovie.title)
        assert(remoteMovie.overview == localMovie.overview)
        assert(remoteMovie.adult == localMovie.isOnlyForAdults)
        assert(remoteMovie.poster_path == localMovie.posterUrl)
        assert(remoteMovie.vote_average == localMovie.voteAverage)
        assert(remoteMovie.release_date == localMovie.releaseDate)
    }
}