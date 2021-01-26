package com.example.moviesapp.presentation.mapper

import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeMovie
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeMovies
import com.example.moviesapp.presentation.model.PresentationMovie
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
        assert(presentationMovie.id == movie.id)
        assert(presentationMovie.title == movie.title)
        assert(presentationMovie.overview == movie.overview)
        assert(presentationMovie.isOnlyForAdults == movie.isOnlyForAdults)
        assert(presentationMovie.posterUrl == movie.posterUrl)
        assert(presentationMovie.voteAverage == movie.voteAverage.toString())
        assert(presentationMovie.releaseDate == movie.releaseDate)
    }
}