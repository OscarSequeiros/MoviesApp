package com.example.moviesapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.moviesapp.databinding.FragmentMoviesBinding
import com.example.moviesapp.presentation.MoviesState
import com.example.moviesapp.presentation.MoviesState.*
import com.example.moviesapp.presentation.model.PresentationMovie
import com.example.moviesapp.presentation.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var binding: FragmentMoviesBinding? = null

    private val viewModel: MoviesViewModel by viewModels()

    private val adapter = MoviesAdapter { movieId ->
        Toast.makeText(context, "Presionaste $movieId", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMoviesBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStates()
        setupAdapter()
    }

    private fun observeStates() {
        lifecycleScope.launch {
            viewModel
                .stateFlow
                .collect { state -> render(state) }
        }
    }

    private fun setupAdapter() {
        binding?.recyclerMovies?.adapter = adapter
    }

    private fun render(state: MoviesState) {
        when(state) {
            is IdleState -> showIdleState()
            is LoadingState -> showLoading()
            is FailureState -> showFailure()
            is EmptyMoviesState -> showEmptyMovies()
            is FilledMoviesState -> showMovies(state.movies)
        }
    }

    private fun showIdleState() {

    }

    private fun showLoading() {
        binding?.progressLoading?.visibility = View.VISIBLE
    }

    private fun showFailure() {
        binding?.progressLoading?.visibility = View.GONE
        binding?.imageFailure?.visibility = View.VISIBLE
    }

    private fun showEmptyMovies() {
        binding?.progressLoading?.visibility = View.GONE
        binding?.imageEmpty?.visibility = View.VISIBLE
    }

    private fun showMovies(movies: List<PresentationMovie>) {
        binding?.progressLoading?.visibility = View.GONE
        binding?.recyclerMovies?.visibility = View.VISIBLE
        adapter.movies = movies
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}