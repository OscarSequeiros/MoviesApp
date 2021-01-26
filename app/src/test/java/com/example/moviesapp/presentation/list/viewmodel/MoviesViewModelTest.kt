package com.example.moviesapp.presentation.list.viewmodel

import com.example.moviesapp.CoroutineTestRule
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.usecase.GetPopularMoviesUseCase
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeMovies
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakePresentationMovies
import com.example.moviesapp.presentation.list.event.MoviesEvent.OpenMoviesEvent
import com.example.moviesapp.presentation.list.state.MoviesState
import com.example.moviesapp.presentation.mapper.PresentationMovieMapper
import com.example.moviesapp.presentation.model.PresentationMovie
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private val useCase = mockk<GetPopularMoviesUseCase>()
    private val mapper = mockk<PresentationMovieMapper>()

    private val viewModel = MoviesViewModel(
        getMoviesUseCase = useCase,
        mapper = mapper
    )

    @Test
    fun `given movies by use case, when process OpenMoviesEvent, then expose FilledMoviesState`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val fakeMovies = makeFakeMovies(5)
            stubMoviesInUseCase(fakeMovies)
            val fakePresentationMovies = makeFakePresentationMovies(5)
            stubToPresentationMapper(fakePresentationMovies)

            viewModel.processEvent(OpenMoviesEvent)

            viewModel.stateFlow.value shouldBe  MoviesState.FilledMoviesState(fakePresentationMovies)
        }

    @Test
    fun `given empty movies list by use case, when process OpenMoviesEvent, then expose EmptyMoviesState`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val fakeMovies = makeFakeMovies(0)
            stubMoviesInUseCase(fakeMovies)
            val fakePresentationMovies = makeFakePresentationMovies(0)
            stubToPresentationMapper(fakePresentationMovies)

            viewModel.processEvent(OpenMoviesEvent)

            viewModel.stateFlow.value shouldBe  MoviesState.EmptyMoviesState
        }

    private fun stubMoviesInUseCase(movies: List<Movie>) {
        coEvery { useCase() } coAnswers { flowOf(movies) }
    }

    private fun stubToPresentationMapper(presentationMovies: List<PresentationMovie>) {
        every { mapper.toPresentation(any<List<Movie>>()) } returns presentationMovies
    }

    @Test
    fun `given exception by use case, when process OpenMoviesEvent, then expose FailureState`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val error = Throwable()
            stubErrorInUseCase(error)

            viewModel.processEvent(OpenMoviesEvent)

            viewModel.stateFlow.value shouldBe MoviesState.FailureState(error)
        }

    private fun stubErrorInUseCase(error: Throwable) {
        coEvery { useCase() } coAnswers { flow { throw error } }
    }
}