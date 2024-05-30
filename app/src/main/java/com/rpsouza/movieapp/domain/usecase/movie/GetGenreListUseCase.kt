package com.rpsouza.movieapp.domain.usecase.movie

import com.rpsouza.movieapp.data.mapper.toDomain
import com.rpsouza.movieapp.domain.model.gener.Genre
import com.rpsouza.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetGenreListUseCase @Inject constructor(
  private val movieRepository: MovieRepository
) {
  suspend operator fun invoke(apiKey: String, language: String): List<Genre> {
    return movieRepository.getGenreList(apiKey, language).genres?.map { it.toDomain() } ?: emptyList()
  }
}