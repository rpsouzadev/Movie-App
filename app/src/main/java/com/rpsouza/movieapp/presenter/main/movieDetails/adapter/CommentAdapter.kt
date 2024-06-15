package com.rpsouza.movieapp.presenter.main.movieDetails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.CastItemBinding
import com.rpsouza.movieapp.databinding.ItemCommentReviewBinding
import com.rpsouza.movieapp.domain.model.review.MovieReview
import com.rpsouza.movieapp.utils.formatCommentDate

class CommentAdapter(
    private val context: Context,
) : ListAdapter<MovieReview, CommentAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieReview>() {
            override fun areItemsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemCommentReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val comment = getItem(position)

        comment.authorDetails?.avatarPath?.let { avatarPath ->
            Glide
                .with(context)
                .load("https://image.tmdb.org/t/p/w500$avatarPath")
                .into(holder.binding.imageUser)
        } ?: run {
            holder.binding.imageUser.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.binding.root.context,
                    R.drawable.ic_person
                )
            )
        }

        holder.binding.textUsername.text = comment?.authorDetails?.username
        holder.binding.textComment.text = comment?.content
        holder.binding.textRatingReview.text = comment.authorDetails?.rating?.toString() ?: "0"
        holder.binding.textDate.text = formatCommentDate(comment?.createdAt)
    }

    inner class MyViewHolder(val binding: ItemCommentReviewBinding) :
        RecyclerView.ViewHolder(binding.root)

}