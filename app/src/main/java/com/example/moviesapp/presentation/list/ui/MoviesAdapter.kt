package com.example.moviesapp.presentation.list.ui

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ItemMovieBinding
import com.example.moviesapp.presentation.model.PresentationMovie
import javax.inject.Inject
import kotlin.properties.Delegates

class MoviesAdapter @Inject constructor(
    private val clickListener: (movieId: Long) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    var movies: List<PresentationMovie> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

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
            textOverview.text = movie.overview
            constraintContainer.setOnClickListener { clickListener.invoke(movie.id) }
            loadImage(movie.posterUrl)
            textRating.text = movie.voteAverage
        }

        private fun ItemMovieBinding.loadImage(posterUrl: String?) {
            imagePoster.load(posterUrl) {
                placeholder(R.drawable.movie_placeholder)
                error(R.drawable.movie_placeholder)
            }
        }
    }
}