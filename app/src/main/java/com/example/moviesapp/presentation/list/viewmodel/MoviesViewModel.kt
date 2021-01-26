package com.example.moviesapp.presentation.list.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.usecase.GetPopularMoviesUseCase
import com.example.moviesapp.presentation.list.state.MoviesState
import com.example.moviesapp.presentation.list.state.MoviesState.*
import com.example.moviesapp.presentation.mapper.PresentationMovieMapper
import com.example.moviesapp.presentation.model.PresentationMovie
import kotlinx.coroutines.flow.*

class MoviesViewModel @ViewModelInject constructor(
    private val getMoviesUseCase: GetPopularMoviesUseCase,
    private val mapper: PresentationMovieMapper
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<MoviesState>(IdleState)

    val stateFlow: StateFlow<MoviesState>
        get() = _stateFlow

    fun getPopularMovies() {
        getMoviesUseCase()
            .map { movies -> mapper.toPresentation(movies) }
            .map { movies -> movies.toState() }
            .onStart { emit(LoadingState) }
            .catch { error -> emit(FailureState(error)) }
            .onEach { state -> _stateFlow.value = state }
            .launchIn(viewModelScope)
    }

    private fun List<PresentationMovie>.toState(): MoviesState {
        return if (isEmpty()) EmptyMoviesState else FilledMoviesState(this)
    }
}