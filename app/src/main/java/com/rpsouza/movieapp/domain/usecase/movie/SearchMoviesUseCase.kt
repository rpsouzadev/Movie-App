package com.rpsouza.movieapp.domain.usecase.movie

import com.rpsouza.movieapp.data.mapper.toDomain
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
  private val movieRepository: MovieRepository
) {

  suspend operator fun invoke(apiKey: String, language: String, query: String): List<Movie> {
    return movieRepository.searchMovies(
      apiKey = apiKey,
      language = language,
      query = query
    ).filter { it.backdropPath != null }.map { it.toDomain() }
  }
}