package com.rpsouza.movieapp.domain.model.favorite

import android.os.Parcelable
import com.rpsouza.movieapp.domain.model.gener.Genre
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteMovie(
    val id: Int? = null,
    val backdropPath: String? = null,
    val genres: List<Genre>? = null,
    val title: String? = null,
    val voteAverage: String? = null,
) : Parcelable
