package com.rpsouza.movieapp.presenter.main.movieDetails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rpsouza.movieapp.databinding.CastItemBinding
import com.rpsouza.movieapp.domain.model.cast.Cast

class CastAdapter(
  private val context: Context,
) : ListAdapter<Cast, CastAdapter.MyViewHolder>(DIFF_CALLBACK) {

  companion object {
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cast>() {
      override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem == newItem
      }

    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    return MyViewHolder(
      CastItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )

  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val cast = getItem(position)

    Glide
      .with(context)
      .load(cast?.profilePath)
      .into(holder.binding.castImage)

    holder.binding.textCastName.text = cast?.name
    holder.binding.textCharacter.text = cast?.character
  }

  inner class MyViewHolder(val binding: CastItemBinding) : RecyclerView.ViewHolder(binding.root)

}