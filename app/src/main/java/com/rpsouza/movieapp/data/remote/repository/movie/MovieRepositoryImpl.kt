package com.rpsouza.movieapp.data.remote.repository.movie

import androidx.paging.PagingSource
import com.rpsouza.movieapp.data.api.ServiceAPI
import com.rpsouza.movieapp.data.paging.MovieByGenrePagingSource
import com.rpsouza.movieapp.data.paging.SearchMoviePagingSource
import com.rpsouza.movieapp.data.remote.model.gener.GenreListResponse
import com.rpsouza.movieapp.data.remote.model.movie.MovieResponse
import com.rpsouza.movieapp.domain.remote.repository.movie.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
  private val serviceAPI: ServiceAPI
) : MovieRepository {
  override suspend fun getGenreList(apiKey: String, language: String?): GenreListResponse {
    return serviceAPI.getGenreList(apiKey = apiKey, language = language)
  }

  override fun getMovieByGenre(
    apiKey: String,
    language: String?,
    genreId: Int?
  ): PagingSource<Int, MovieResponse> {
    return MovieByGenrePagingSource(serviceAPI, genreId)
  }

  override fun searchMovies(
    apiKey: String,
    language: String?,
    query: String
  ): PagingSource<Int, MovieResponse> {
    return SearchMoviePagingSource(serviceAPI, query)
  }
}