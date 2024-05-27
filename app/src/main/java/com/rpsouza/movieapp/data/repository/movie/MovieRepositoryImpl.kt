package com.rpsouza.movieapp.data.repository.movie

import com.rpsouza.movieapp.data.api.ServiceAPI
import com.rpsouza.movieapp.data.model.gener.GenreListResponse
import com.rpsouza.movieapp.data.model.movie.MovieResponse
import com.rpsouza.movieapp.domain.repository.movie.MovieRepository
import com.rpsouza.movieapp.utils.Constants
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
  private val serviceAPI: ServiceAPI
) : MovieRepository {
  override suspend fun getGenreList(language: String?): GenreListResponse {
    return serviceAPI.getGenreList(
      language = Constants.Movie.LANGUAGE
    )
  }

  override suspend fun getMovieByGenre(language: String?, genreId: Int?): List<MovieResponse> {
    return serviceAPI.getMovieByGenre(
      language = Constants.Movie.LANGUAGE,
      genreId = genreId
    ).results ?: emptyList()
  }
}