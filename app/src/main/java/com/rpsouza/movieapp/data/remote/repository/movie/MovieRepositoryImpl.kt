package com.rpsouza.movieapp.data.remote.repository.movie

import com.rpsouza.movieapp.data.api.ServiceAPI
import com.rpsouza.movieapp.data.remote.model.cast.CastResponse
import com.rpsouza.movieapp.data.remote.model.gener.GenreListResponse
import com.rpsouza.movieapp.data.remote.model.movie.MovieResponse
import com.rpsouza.movieapp.domain.remote.repository.movie.MovieRepository
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class MovieRepositoryImpl @Inject constructor(
  private val serviceAPI: ServiceAPI
) : MovieRepository {
  override suspend fun getGenreList(apiKey: String, language: String?): GenreListResponse {
    return serviceAPI.getGenreList(apiKey = apiKey, language = language)
  }

  override suspend fun getMovieByGenre(
    apiKey: String,
    language: String?,
    genreId: Int?
  ): List<MovieResponse> {
    return serviceAPI.getMovieByGenre(
      apiKey = apiKey,
      language = language,
      genreId = genreId
    ).results ?: emptyList()
  }

  override suspend fun searchMovies(
    apiKey: String,
    language: String?,
    query: String
  ): List<MovieResponse> {
    return serviceAPI.searchMovies(
      apiKey = apiKey,
      language = language,
      query = query
    ).results ?: emptyList()
  }
}