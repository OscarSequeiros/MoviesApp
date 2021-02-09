package com.example.moviesapp.presentation.list.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.example.moviesapp.R
import com.example.moviesapp.di.replace
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.usecase.GetPopularMoviesUseCase
import com.example.moviesapp.launchFragmentInHiltContainer
import com.example.moviesapp.presentation.list.viewmodel.MoviesViewModel
import com.example.moviesapp.presentation.mapper.PresentationMovieMapper
import com.example.moviesapp.presentation.model.PresentationMovie
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class MoviesFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val useCase = Mockito.mock(GetPopularMoviesUseCase::class.java)
    private val mapper = Mockito.mock(PresentationMovieMapper::class.java)
    private val viewModelMock = MoviesViewModel(useCase, mapper)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun when_open_detail_event_show_detail() {
        val movies: List<Movie> = emptyList()
        stubMoviesInUseCase(movies)
        stubToPresentationMapper(movies, emptyList())
        launchFragmentInHiltContainer<MoviesFragment> {
            replace(MoviesFragment::viewModel, viewModelMock)
        }

        Espresso.onView(ViewMatchers.withId(R.id.progress_loading))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    private fun stubMoviesInUseCase(movies: List<Movie>) {
        Mockito.`when`(useCase()).thenReturn(flowOf(movies))
    }

    private fun stubToPresentationMapper(movies: List<Movie>, presentationMovies: List<PresentationMovie>) {
        Mockito.`when`(mapper.toPresentation(movies)).thenReturn(presentationMovies)
    }
}