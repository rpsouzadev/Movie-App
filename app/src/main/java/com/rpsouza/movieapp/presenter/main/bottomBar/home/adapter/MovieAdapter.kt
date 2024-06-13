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
import com.rpsouza.movieapp.domain.model.movie.Movie

class MovieAdapter(
  private val context: Context,
  private val itemInflater: Int,
  private val movieClickListener: (movieId: Int) -> Unit
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
      .load(movie.posterPath)
      .into(holder.movieImage)

    movie.id?.let { movieId ->
      holder.itemView.setOnClickListener { movieClickListener(movieId) }
    }
  }

  inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val movieImage: ImageView = itemView.findViewById(R.id.movie_image)
  }

}