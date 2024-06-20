package com.rpsouza.movieapp.domain.remote.usecase.cast

import com.rpsouza.movieapp.data.mapper.toDomain
import com.rpsouza.movieapp.domain.model.cast.Cast
import com.rpsouza.movieapp.domain.remote.repository.movie.MovieDetailsRepository
import com.rpsouza.movieapp.domain.remote.repository.movie.MovieRepository
import javax.inject.Inject

class GetCreditsUseCase @Inject constructor(
  private val movieDetailsRepository: MovieDetailsRepository
) {
  suspend operator fun invoke(movieId: Int, apiKey: String, language: String): List<Cast> {
    return movieDetailsRepository.getCredits(
      movieId = movieId,
      apiKey = apiKey,
      language = language,
    ).map { it.toDomain() }
  }
}