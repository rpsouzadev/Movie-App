package com.rpsouza.movieapp.presenter.main.bottomBar.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.GenreItemBinding
import com.rpsouza.movieapp.presenter.model.GenrePresentation

class GenreMovieAdapter(
  private val seeAllList: (Int) -> Unit
) : ListAdapter<GenrePresentation, GenreMovieAdapter.MyViewHolder>(DIFF_CALLBACK) {

  companion object {
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GenrePresentation>() {
      override fun areItemsTheSame(
        oldItem: GenrePresentation,
        newItem: GenrePresentation
      ): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(
        oldItem: GenrePresentation,
        newItem: GenrePresentation
      ): Boolean {
        return oldItem == newItem
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    return MyViewHolder(
      GenreItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val genre = getItem(position)

    holder.binding.textGenderName.text = genre.name

    holder.binding.textSeeAll.setOnClickListener {
      genre.id?.let { seeAllList(it) }
    }

    val movieAdapter = MovieAdapter(
      context = holder.binding.root.context,
      itemInflater = R.layout.movie_item
    )
    val layoutManager = LinearLayoutManager(
      holder.binding.root.context,
      LinearLayoutManager.HORIZONTAL,
      false
    )

    holder.binding.recyclerMovies.layoutManager = layoutManager
    holder.binding.recyclerMovies.setHasFixedSize(true)
    holder.binding.recyclerMovies.adapter = movieAdapter
    movieAdapter.submitList(genre.movies)
  }

  inner class MyViewHolder(val binding: GenreItemBinding) : RecyclerView.ViewHolder(binding.root)

}