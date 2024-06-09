package com.rpsouza.movieapp.domain.repository.movie

import com.rpsouza.movieapp.data.model.cast.CastResponse
import com.rpsouza.movieapp.data.model.movie.MovieResponse

interface MovieDetailsRepository {
  suspend fun getMovieDetails(
    movieId: Int,
    apiKey: String,
    language: String?,
  ): MovieResponse

  suspend fun getCredits(
    movieId: Int,
    apiKey: String,
    language: String?,
  ): List<CastResponse>

  suspend fun getMoviesSimilar(
    movieId: Int,
    apiKey: String,
    language: String?,
  ): List<MovieResponse>
}