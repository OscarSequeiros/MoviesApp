package com.example.moviesapp.presentation.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.moviesapp.databinding.FragmentMovieDetailBinding
import com.example.moviesapp.presentation.detail.state.MovieDetailState
import com.example.moviesapp.presentation.detail.state.MovieDetailState.*
import com.example.moviesapp.presentation.detail.viewmodel.MovieDetailViewModel
import com.example.moviesapp.presentation.model.PresentationMovie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var binding: FragmentMovieDetailBinding? = null

    private val viewModel: MovieDetailViewModel by viewModels()

    private val args: MovieDetailFragmentArgs by navArgs()

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
        viewModel.getMovie(args.argMovieId)
    }

    private fun observeStates() {
        lifecycleScope.launch {
            viewModel
                .stateFlow
                .collect { state -> render(state) }
        }
    }

    private fun render(state: MovieDetailState) {
        when (state) {
            is IdleState -> showIdleState()
            is LoadingState -> showLoading()
            is SuccessfulState -> showMovie(state.movie)
            is FailureState -> showFailure(state.error)
        }
    }

    private fun showIdleState() {

    }

    private fun showLoading() {
        binding?.progressLoading?.visibility = View.VISIBLE
    }

    private fun showFailure(error: Throwable) {
        error.printStackTrace()
        binding?.progressLoading?.visibility = View.GONE
        binding?.imageFailure?.visibility = View.VISIBLE
    }

    private fun showMovie(movie: PresentationMovie) {
        binding?.apply {
            progressLoading.visibility = View.GONE
            imagePoster.load(movie.posterUrl)
            textTitle.text = movie.title
            textOverview.text = movie.overview
            textRating.text = movie.voteAverage
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}