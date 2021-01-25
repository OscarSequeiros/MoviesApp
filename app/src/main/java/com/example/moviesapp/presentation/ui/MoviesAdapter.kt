package com.example.moviesapp.presentation.ui

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.ItemMovieBinding
import com.example.moviesapp.presentation.model.PresentationMovie
import javax.inject.Inject

class MoviesAdapter @Inject constructor(
    private val clickListener: (movieId: Long) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var movies: List<PresentationMovie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: PresentationMovie) = with(binding) {
            textTitle.text = movie.title
            cardContainer.setOnClickListener { clickListener.invoke(movie.id) }
        }
    }
}