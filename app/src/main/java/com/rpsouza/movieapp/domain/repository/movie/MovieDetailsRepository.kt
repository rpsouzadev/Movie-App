package com.rpsouza.movieapp.domain.repository.movie

import com.rpsouza.movieapp.data.model.cast.CastResponse
import com.rpsouza.movieapp.data.model.movie.MovieResponse
import com.rpsouza.movieapp.data.model.review.MovieReviewResponse

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