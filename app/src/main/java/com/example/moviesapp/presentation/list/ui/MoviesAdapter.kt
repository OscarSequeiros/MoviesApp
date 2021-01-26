package com.example.moviesapp.presentation.list.ui

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ItemMovieBinding
import com.example.moviesapp.presentation.model.PresentationMovie
import javax.inject.Inject

class MoviesAdapter @Inject constructor(
    private val clickListener: (movieId: Long) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    var movies: List<PresentationMovie> = emptyList()
        set(value)  {
            field = value
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
            imagePoster.load(movie.posterUrl) {
                placeholder(R.drawable.ic_cinema)
            }
            textRating.text = movie.voteAverage
        }
    }
}