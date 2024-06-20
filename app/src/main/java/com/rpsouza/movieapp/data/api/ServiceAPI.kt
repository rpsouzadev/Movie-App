package com.rpsouza.movieapp.data.api

import com.rpsouza.movieapp.data.remote.model.basePagination.BasePaginationResponse
import com.rpsouza.movieapp.data.remote.model.credits.CreditsResponse
import com.rpsouza.movieapp.data.remote.model.gener.GenreListResponse
import com.rpsouza.movieapp.data.remote.model.movie.MovieResponse
import com.rpsouza.movieapp.data.remote.model.review.MovieReviewResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceAPI {

  @GET("genre/movie/list")
  suspend fun getGenreList(
    @Query("api_key") apiKey: String,
    @Query("language") language: String?
  ): GenreListResponse

  @GET("discover/movie")
  suspend fun getMovieByGenre(
    @Query("api_key") apiKey: String,
    @Query("language") language: String?,
    @Query("with_genres") genreId: Int?,
  ): BasePaginationResponse<List<MovieResponse>>

  @GET("search/movie")
  suspend fun searchMovies(
    @Query("api_key") apiKey: String,
    @Query("language") language: String?,
    @Query("query") query: String,
    @Query("include_adult") adult: Boolean = false
  ): BasePaginationResponse<List<MovieResponse>>

  @GET("movie/{movie_id}")
  suspend fun getMovieDetails(
    @Path("movie_id") movieId: Int,
    @Query("api_key") apiKey: String,
    @Query("language") language: String?,
  ): MovieResponse

  @GET("movie/{movie_id}/credits")
  suspend fun getCredits(
    @Path("movie_id") movieId: Int,
    @Query("api_key") apiKey: String,
    @Query("language") language: String?,
  ): CreditsResponse

  @GET("movie/{movie_id}/similar")
  suspend fun getMoviesSimilar(
    @Path("movie_id") movieId: Int,
    @Query("api_key") apiKey: String,
    @Query("language") language: String?,
  ): BasePaginationResponse<List<MovieResponse>>

  @GET("movie/{movie_id}/reviews")
  suspend fun getReviews(
    @Path("movie_id") movieId: Int,
    @Query("api_key") apiKey: String,
  ): BasePaginationResponse<List<MovieReviewResponse>>
}