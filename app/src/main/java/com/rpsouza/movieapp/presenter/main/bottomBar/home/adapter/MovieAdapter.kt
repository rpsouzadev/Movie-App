package com.rpsouza.movieapp.presenter.main.bottomBar.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.MovieItemBinding
import com.rpsouza.movieapp.domain.model.movie.Movie

class MovieAdapter(
  private val context: Context,
  private val itemInflater: Int
) : ListAdapter<Movie, MovieAdapter.MyViewHolder>(DIFF_CALLBACK) {

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
    val view = LayoutInflater.from(parent.context).inflate(itemInflater, parent, false)

    return MyViewHolder(view)
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val movie = getItem(position)

    Glide
      .with(context)
      .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
      .into(holder.movieImage)
  }

  inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val movieImage: ImageView = itemView.findViewById(R.id.movie_image)
  }

}