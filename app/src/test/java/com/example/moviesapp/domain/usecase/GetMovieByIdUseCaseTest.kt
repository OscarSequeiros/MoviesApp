package com.example.moviesapp.domain.usecase

import com.example.moviesapp.FakeMoviesFactory.makeFakeMovie
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetMovieByIdUseCaseTest {

    private val repository = mockk<MoviesRepository>()

    private val useCase = GetMovieByIdUseCase(repository)

    @Test
    fun `given movie by repository, when invoke, then get the same movie`() = runBlocking {
        val fakeMovie = makeFakeMovie()
        val fakeId = 12443L
        stubRepository(fakeMovie)

        val movie = useCase(fakeId)

        assert(fakeMovie == movie)
    }

    private fun stubRepository(movie: Movie) {
        coEvery { repository.getMovieById(any()) } coAnswers { movie }
    }
}