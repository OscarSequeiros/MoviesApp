package com.example.moviesapp.presentation.detail.viewmodel

import com.example.moviesapp.CoroutineTestRule
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.usecase.GetMovieByIdUseCase
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakeMovie
import com.example.moviesapp.factory.FakeMoviesFactory.makeFakePresentationMovie
import com.example.moviesapp.presentation.detail.event.MovieDetailEvent.OpenMovieDetailsEvent
import com.example.moviesapp.presentation.detail.state.MovieDetailState
import com.example.moviesapp.presentation.mapper.PresentationMovieMapper
import com.example.moviesapp.presentation.model.PresentationMovie
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private val useCase = mockk<GetMovieByIdUseCase>()
    private val mapper = mockk<PresentationMovieMapper>()

    private val viewModel = MovieDetailViewModel(
        getMovieByIdUseCase = useCase,
        mapper = mapper
    )

    @Test
    fun `given movie by use case, when process OpenMovieDetailsEvent, then expose SuccessfulState`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val fakeMovie = makeFakeMovie()
            stubMovieInUseCase(fakeMovie)
            val fakePresentationMovie = makeFakePresentationMovie()
            stubToPresentationMapper(fakePresentationMovie)
            val fakeMovieId = 43L

            viewModel.processEvent(OpenMovieDetailsEvent(fakeMovieId))

            viewModel.stateFlow.value shouldBe MovieDetailState.SuccessfulState(fakePresentationMovie)
        }

    private fun stubMovieInUseCase(movie: Movie) {
        coEvery { useCase(any()) } coAnswers { movie }
    }

    private fun stubToPresentationMapper(presentationMovie: PresentationMovie) {
        every { mapper.toPresentation(any<Movie>()) } returns presentationMovie
    }

    @Test
    fun `given exception by use case, when process OpenMovieDetailsEvent, then expose FailureState`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val fakeError = Throwable()
            stubErrorInUseCase(fakeError)
            val fakeMovieId = 43L

            viewModel.processEvent(OpenMovieDetailsEvent(fakeMovieId))

            viewModel.stateFlow.value shouldBe MovieDetailState.FailureState(fakeError)
        }

    private fun stubErrorInUseCase(error: Throwable) {
        coEvery { useCase(any()) } coAnswers { throw error }
    }
}