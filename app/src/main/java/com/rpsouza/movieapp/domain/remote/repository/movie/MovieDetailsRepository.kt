package com.rpsouza.movieapp.domain.remote.repository.movie

import com.rpsouza.movieapp.data.remote.model.cast.CastResponse
import com.rpsouza.movieapp.data.remote.model.movie.MovieResponse
import com.rpsouza.movieapp.data.remote.model.review.MovieReviewResponse

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

  suspend fun getReviews(
    movieId: Int,
    apiKey: String,
  ): List<MovieReviewResponse>
}