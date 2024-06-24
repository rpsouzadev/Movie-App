package com.rpsouza.movieapp.data.remote.model.movie

import com.google.gson.annotations.SerializedName
import com.rpsouza.movieapp.data.remote.model.gener.GenreResponse

data class MovieResponse(
    val adult: Boolean?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val genres: List<GenreResponse>?,
    val id: Int?,

    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String?,
    val popularity: Float?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,

    @SerializedName("vote_average")
    val voteAverage: Float?,

    @SerializedName("vote_count")
    val voteCount: Int?,

    @SerializedName("production_countries")
    val productionCountries: List<CountryResponse>?,
    val runtime: Int?,
)