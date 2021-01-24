package com.example.moviesapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.moviesapp.domain.usecase.GetPopularMoviesUseCase
import com.example.moviesapp.presentation.mapper.PresentationMovieMapper

class MoviesViewModel(
    private val getMoviesUseCase: GetPopularMoviesUseCase,
    private val mapper: PresentationMovieMapper
) : ViewModel() {

    private val
}