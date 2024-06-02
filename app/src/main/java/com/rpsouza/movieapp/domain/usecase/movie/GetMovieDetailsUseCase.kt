package com.rpsouza.movieapp.domain.usecase.movie

import com.rpsouza.movieapp.data.mapper.toDomain
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
  private val movieRepository: MovieRepository
) {

  suspend operator fun invoke(movieId: Int, apiKey: String, language: String): Movie {
    return movieRepository.getMovieDetails(
      movieId = movieId,
      apiKey = apiKey,
      language = language,
    ).toDomain()
  }
}