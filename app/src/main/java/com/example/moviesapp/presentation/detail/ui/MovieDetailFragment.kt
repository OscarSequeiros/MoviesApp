package com.example.moviesapp.presentation.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.moviesapp.databinding.FragmentMovieDetailBinding
import com.example.moviesapp.presentation.detail.state.MovieDetailState
import com.example.moviesapp.presentation.detail.state.MovieDetailState.*
import com.example.moviesapp.presentation.model.PresentationMovie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var binding: FragmentMovieDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMovieDetailBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStates()
    }

    private fun observeStates() {
        lifecycleScope.launch {
            /*viewModel
                .stateFlow
                .collect { state -> render(state) }*/
        }
    }

    private fun render(state: MovieDetailState) {
        when (state) {
            is IdleState -> showIdleState()
            is LoadingState -> showLoading()
            is SuccessfulState -> showMovie(state.movie)
            is FailureState -> showFailure()
        }
    }

    private fun showIdleState() {

    }

    private fun showLoading() {

    }

    private fun showFailure() {

    }

    private fun showMovie(movie: PresentationMovie) {

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}