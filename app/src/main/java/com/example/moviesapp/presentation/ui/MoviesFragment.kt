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
import com.example.moviesapp.presentation.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var binding: FragmentMoviesBinding? = null

    private val viewModel: MoviesViewModel by viewModels()

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
    }

    private fun observeStates() {
        lifecycleScope.launch {
            viewModel
                .stateFlow
                .collect { state -> render(state) }
        }
    }

    private fun render(state: MoviesState) {
        when(state) {
            is IdleState -> Toast.makeText(context, "idle", Toast.LENGTH_SHORT).show()
            is FailureState -> Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show()
            is LoadingState -> Toast.makeText(context, "loading", Toast.LENGTH_SHORT).show()
            is EmptyMoviesState -> Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show()
            is FilledMoviesState -> Toast.makeText(context, "filled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}