package com.rpsouza.movieapp.presenter.main.movieGenre.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.MovieGenreItemBinding
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.utils.circularProgressDrawable

class MoviePagingAdapter(
  private val context: Context,
  private val movieClickListener: (movieId: Int) -> Unit
) : PagingDataAdapter<Movie, MoviePagingAdapter.MyViewHolder>(DIFF_CALLBACK) {

  companion object {
    val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Movie>() {
      override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
      }

    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    return MyViewHolder(
      MovieGenreItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val movie = getItem(position)

    Glide
      .with(context)
      .load(movie?.posterPath)
      .placeholder(context.circularProgressDrawable())
      .into(holder.binding.movieImage)

    movie?.id?.let { movieId ->
      holder.itemView.setOnClickListener { movieClickListener(movieId) }
    }
  }

  inner class MyViewHolder(val binding: MovieGenreItemBinding) : RecyclerView.ViewHolder(binding.root)

}