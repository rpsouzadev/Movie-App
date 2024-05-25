package com.rpsouza.movieapp.presenter.model

import android.os.Parcelable
import com.rpsouza.movieapp.domain.model.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenrePresentation(
  val id: Int?,
  val name: String?,
  val movies: List<Movie>?
) : Parcelable