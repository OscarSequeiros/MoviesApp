package com.example.moviesapp.data.local

import com.example.moviesapp.data.local.database.MoviesAppDataBase
import com.example.moviesapp.data.local.model.LocalMovie
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeLocalMovie
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeLocalMovies
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesLocalSourceTest {

    private val database = mockk<MoviesAppDataBase>()

    private val localSource = MoviesLocalSource(database)

    @Test
    fun `given popular movies from db, when getAllMovies, then get the same list`() = runBlockingTest {
        val fakeMovies = makeFakeLocalMovies(6)
        stubMoviesInDatabase(fakeMovies)

        val moviesFlow = localSource.getAllMovies()

        moviesFlow.collect { movies -> fakeMovies shouldBe movies }
    }

    private fun stubMoviesInDatabase(movies: List<LocalMovie>) {
        coEvery { database.movieDao().getAllPopulars() } coAnswers { flowOf(movies) }
    }

    @Test
    fun `given movie from db, when getMovieById, then get the same movie`() = runBlockingTest {
        val fakeMovie = makeFakeLocalMovie()
        stubMovieInDatabase(fakeMovie)
        val fakeMovieId = 41L

        val movie = localSource.getMovieById(fakeMovieId)

        fakeMovie shouldBe movie
    }

    private fun stubMovieInDatabase(movie: LocalMovie) {
        coEvery { database.movieDao().getById(any()) } coAnswers { movie }
    }
}