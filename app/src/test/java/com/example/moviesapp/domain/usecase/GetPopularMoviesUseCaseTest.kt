package com.example.moviesapp.domain.usecase

import com.example.moviesapp.factory.FakeMoviesFactory
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetPopularMoviesUseCaseTest {

    private val repository = mockk<MoviesRepository>()

    private val useCase = GetPopularMoviesUseCase(repository)

    @Test
    fun `given movies by repository, when invoke, then get the same movie's list`() = runBlocking {
        val fakeMovies = FakeMoviesFactory.makeFakeMovies()
        stubRepository(fakeMovies)

        val flowMovies = useCase.invoke()

        flowMovies.collect { movies ->
            assert(fakeMovies == movies)
        }
    }

    private fun stubRepository(movies: List<Movie>) {
        coEvery { repository.getPopularMovies() } coAnswers { flowOf(movies) }
    }
}