package com.rpsouza.movieapp.domain.remote.usecase.movie

import com.rpsouza.movieapp.data.mapper.toDomain
import com.rpsouza.movieapp.domain.model.gener.Genre
import com.rpsouza.movieapp.domain.remote.repository.movie.MovieRepository
import javax.inject.Inject

class GetGenreListUseCase @Inject constructor(
  private val movieRepository: MovieRepository
) {
  suspend operator fun invoke(): List<Genre> {
    return movieRepository.getGenreList().genres?.map { it.toDomain() } ?: emptyList()
  }
}