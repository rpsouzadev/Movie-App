package com.rpsouza.movieapp.domain.model.movie

import android.os.Parcelable
import com.rpsouza.movieapp.domain.model.gener.Genre
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val adult: Boolean?,
    val backdropPath: String?,
    val genres: List<Genre>?,
    val id: Int?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Float?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Float?,
    val voteCount: Int?,
    val productionCountries: List<Country>?,
): Parcelable
