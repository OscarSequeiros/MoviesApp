package com.example.moviesapp.presentation.detail.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.usecase.GetMovieByIdUseCase
import com.example.moviesapp.presentation.detail.state.MovieDetailState
import com.example.moviesapp.presentation.detail.state.MovieDetailState.FailureState
import com.example.moviesapp.presentation.detail.state.MovieDetailState.IdleState
import com.example.moviesapp.presentation.mapper.PresentationMovieMapper
import kotlinx.coroutines.flow.*

class MovieDetailViewModel @ViewModelInject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val mapper: PresentationMovieMapper
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<MovieDetailState>(IdleState)

    val stateFlow: StateFlow<MovieDetailState>
        get() = _stateFlow

    fun getMovie(id: Long) {
        flow {
            val movie = getMovieByIdUseCase(0)
            emit(movie)
        }
            .map { movie -> mapper.toPresentation(movie) }
            .map { movie -> MovieDetailState.SuccessfulState(movie) as MovieDetailState }
            .onStart { emit(MovieDetailState.LoadingState) }
            .catch { error ->
                error.printStackTrace()
                emit(FailureState)
            }
            .onEach { state -> _stateFlow.value = state }
            .launchIn(viewModelScope)
    }
}