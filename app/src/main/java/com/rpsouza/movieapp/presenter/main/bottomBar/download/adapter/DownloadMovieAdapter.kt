package com.rpsouza.movieapp.presenter.main.bottomBar.download.adapter

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
import com.rpsouza.movieapp.databinding.MovieDownloadItemBinding
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.utils.calculateFileSize
import com.rpsouza.movieapp.utils.calculateMovieTime

class DownloadMovieAdapter(
    private val context: Context,
    private val detailsClickListener: (movieId: Int) -> Unit,
    private val deleteClickListener: (movie: Movie) -> Unit,
) : ListAdapter<Movie, DownloadMovieAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
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
            MovieDownloadItemBinding.inflate(
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
            .load(movie.posterPath)
            .into(holder.binding.ivMovie)

        holder.binding.textMovie.text = movie.title
        holder.binding.textDuration.text = movie.runtime?.calculateMovieTime()
        holder.binding.textSize.text = movie.runtime?.toDouble()?.calculateFileSize()

        movie.id?.let { movieId ->
            holder.binding.ivMovie.setOnClickListener { detailsClickListener(movieId) }
        }

        holder.binding.ibDelete.setOnClickListener { deleteClickListener(movie) }
    }

    inner class MyViewHolder(val binding: MovieDownloadItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}