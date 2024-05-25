package com.rpsouza.movieapp.data.api

import com.rpsouza.movieapp.data.model.basePagination.BasePaginationResponse
import com.rpsouza.movieapp.data.model.gener.GenreListResponse
import com.rpsouza.movieapp.data.model.movie.MovieResponse
import org.intellij.lang.annotations.Language
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceAPI {

  @GET("genre/movie/list")
  suspend fun getGenreList(@Query("language") language: String?): GenreListResponse

  @GET("discover/movie")
  suspend fun getMovieByGenre(
    @Query("language") language: String?,
    @Query("with_genres") genreId: Int?,
  ): BasePaginationResponse<List<MovieResponse>>
}