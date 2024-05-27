package com.rpsouza.movieapp.domain.usecase.movie

import com.rpsouza.movieapp.data.mapper.toDomain
import com.rpsouza.movieapp.domain.model.gener.Genre
import com.rpsouza.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetGenreListUseCase @Inject constructor(
  private val movieRepository: MovieRepository
) {

  suspend operator fun invoke(language: String): List<Genre> {
    return movieRepository.getGenreList(language).genres?.map { it.toDomain() } ?: emptyList()
  }
}