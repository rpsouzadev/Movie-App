package com.rpsouza.movieapp.domain.repository.movie

import com.rpsouza.movieapp.data.model.gener.GenreListResponse
import com.rpsouza.movieapp.data.model.movie.MovieResponse

interface MovieRepository {

  suspend fun getGenreList(apiKey: String, language: String?): GenreListResponse

  suspend fun getMovieByGenre(
    apiKey: String,
    language: String?,
    genreId: Int?
  ): List<MovieResponse>
}