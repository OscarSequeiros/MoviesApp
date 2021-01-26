package com.example.moviesapp.presentation.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.moviesapp.databinding.FragmentMoviesBinding
import com.example.moviesapp.presentation.list.state.MoviesState
import com.example.moviesapp.presentation.list.state.MoviesState.*
import com.example.moviesapp.presentation.list.viewmodel.MoviesViewModel
import com.example.moviesapp.presentation.model.PresentationMovie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var binding: FragmentMoviesBinding? = null

    private val viewModel: MoviesViewModel by viewModels()

    private val adapter = MoviesAdapter { movieId ->
        val direction = MoviesFragmentDirections.actionToDetail(movieId)
        binding?.root?.findNavController()?.navigate(direction)
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
        viewModel.getPopularMovies()
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
        when (state) {
            is LoadingState -> showLoading()
            is FailureState -> showFailure(state.error)
            is EmptyMoviesState -> showEmptyMovies()
            is FilledMoviesState -> showMovies(state.movies)
        }
    }

    private fun showLoading() {
        binding?.progressLoading?.visibility = View.VISIBLE
    }

    private fun showFailure(error: Throwable) {
        Timber.d(error)
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