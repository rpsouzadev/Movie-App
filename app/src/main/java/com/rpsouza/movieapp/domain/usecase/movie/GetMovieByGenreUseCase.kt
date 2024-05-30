package com.rpsouza.movieapp.domain.usecase.movie

import com.rpsouza.movieapp.data.mapper.toDomain
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetMovieByGenreUseCase @Inject constructor(
  private val movieRepository: MovieRepository
) {

  suspend operator fun invoke(apiKey: String, language: String,  genreId: Int?): List<Movie> {
    return movieRepository.getMovieByGenre(apiKey, language, genreId).map { it.toDomain() }
  }
}