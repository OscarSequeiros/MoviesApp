package com.example.moviesapp.presentation.mapper

import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeMovie
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeMovies
import com.example.moviesapp.presentation.model.PresentationMovie
import io.kotest.matchers.shouldBe
import org.junit.Test

class PresentationMovieMapperTest {

    private val mapper = PresentationMovieMapper()

    @Test
    fun `given movie, when toPresentation, get presentationMovie`() {
        val fakeMovie = makeFakeMovie()

        val presentationMovie = mapper.toPresentation(fakeMovie)

        assertAllFields(presentationMovie, fakeMovie)
    }

    @Test
    fun `given movie's list, when toPresentation, get presentationMovie's list`() {
        val fakeMovies = makeFakeMovies(3)

        val presentationMovies = mapper.toPresentation(fakeMovies)

        presentationMovies.zip(fakeMovies)
            .forEach { (presentationMovie, movie) -> assertAllFields(presentationMovie, movie) }
    }

    private fun assertAllFields(presentationMovie: PresentationMovie, movie: Movie) {
        presentationMovie.id shouldBe movie.id
        presentationMovie.title shouldBe movie.title
        presentationMovie.overview shouldBe movie.overview
        presentationMovie.isOnlyForAdults shouldBe movie.isOnlyForAdults
        presentationMovie.posterUrl shouldBe movie.posterUrl
        presentationMovie.voteAverage shouldBe  movie.voteAverage.toString()
        presentationMovie.releaseDate shouldBe  movie.releaseDate
    }
}