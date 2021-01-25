package com.example.moviesapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.usecase.GetPopularMoviesUseCase
import com.example.moviesapp.presentation.MoviesState
import com.example.moviesapp.presentation.MoviesState.*
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

    init {
        getMoviesUseCase()
                .map { movies -> mapper.toPresentation(movies) }
                .map { movies -> movies.defineState() }
                .onEach { state -> _stateFlow.value = state }
                .onStart { emit(LoadingState) }
                .launchIn(viewModelScope)
    }

    private fun List<PresentationMovie>.defineState(): MoviesState {
        return if (isEmpty()) EmptyMoviesState else FilledMoviesState(this)
    }
}