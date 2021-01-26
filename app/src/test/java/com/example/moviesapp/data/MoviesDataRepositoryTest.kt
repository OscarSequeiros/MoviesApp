package com.example.moviesapp.data

import com.example.moviesapp.data.local.MoviesLocalSource
import com.example.moviesapp.data.local.model.LocalMovie
import com.example.moviesapp.data.mapper.DataMovieMapper
import com.example.moviesapp.data.remote.MoviesRemoteSource
import com.example.moviesapp.data.remote.model.RemoteMovie
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeLocalMovie
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeLocalMovies
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeMovie
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeMovies
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeRemoteMovies
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class MoviesDataRepositoryTest {

    private val remoteSource = mockk<MoviesRemoteSource>()
    private val localSource = mockk<MoviesLocalSource>()
    private val mapper = mockk<DataMovieMapper>()

    private val dataRepository = MoviesDataRepository(
        remoteSource = remoteSource,
        localSource = localSource,
        mapper = mapper
    )

    @Test
    fun `given localMovie by localSource, when getMovieById, then return movie`() = runBlocking {
        val fakeLocalMovie = makeFakeLocalMovie()
        stubMovieInLocalSource(fakeLocalMovie)
        val fakeMovieId = 42L
        val fakeMovie = makeFakeMovie()
        stubMapper(fakeMovie)

        val movie = dataRepository.getMovieById(fakeMovieId)

        fakeMovie shouldBe movie
    }

    private fun stubMovieInLocalSource(localMovie: LocalMovie) {
        coEvery { localSource.getMovieById(any()) } coAnswers { localMovie }
    }

    private fun stubMapper(movie: Movie) {
        every { mapper.toDomain(any<LocalMovie>()) } returns movie
    }

    @Test
    fun `given remoteMovies by remoteSource, when getPopularMovies, then save them in localSource and return`() =
        runBlocking {
            val fakeRemoteMovies = makeFakeRemoteMovies()
            stubRemoteSource(fakeRemoteMovies)
            val fakeLocalMovies = makeFakeLocalMovies()
            stubToLocalMapper(fakeLocalMovies)
            stubMoviesInLocalSource(fakeLocalMovies)
            val fakeMovies = makeFakeMovies()
            stubToDomainMapper(fakeMovies)

            val moviesFlow = dataRepository.getPopularMovies()

            moviesFlow.collect { movies -> fakeMovies shouldBe movies }
            coVerify(exactly = 1) { localSource.insertMovies(fakeLocalMovies) }
        }

    private fun stubRemoteSource(remoteMovies: List<RemoteMovie>) {
        coEvery { remoteSource.getPopularMovies() } coAnswers { remoteMovies }
    }

    private fun stubToLocalMapper(localMovies: List<LocalMovie>) {
        every { mapper.toLocal(any()) } returns localMovies
    }

    @Test
    fun `given exception by remoteSource, when getPopularMovies, then get the previous saved list`() =
        runBlocking {
            stubExceptionInRemoteSource()
            val fakeLocalMovies = makeFakeLocalMovies(2)
            stubMoviesInLocalSource(fakeLocalMovies)
            val fakeMovies = makeFakeMovies()
            stubToDomainMapper(fakeMovies)

            val moviesFlow = dataRepository.getPopularMovies()

            moviesFlow.collect { movies -> fakeMovies shouldBe movies }
            coVerify(exactly = 0) { localSource.insertMovies(any()) }
        }

    private fun stubExceptionInRemoteSource() {
        coEvery { remoteSource.getPopularMovies() } coAnswers { error("") }
    }

    private fun stubMoviesInLocalSource(localMovies: List<LocalMovie>) {
        coEvery { localSource.getAllMovies() } coAnswers { flowOf(localMovies) }
    }

    private fun stubToDomainMapper(movies: List<Movie>) {
        every { mapper.toDomain(any<List<LocalMovie>>()) } returns movies
    }
}