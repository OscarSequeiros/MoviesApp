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

            assert(viewModel.stateFlow.value is MovieDetailState.SuccessfulState)
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
            stubErrorInUseCase()
            val fakeMovieId = 43L

            viewModel.processEvent(OpenMovieDetailsEvent(fakeMovieId))

            assert(viewModel.stateFlow.value is MovieDetailState.FailureState)
        }

    private fun stubErrorInUseCase() {
        coEvery { useCase(any()) } coAnswers { error("") }
    }
}