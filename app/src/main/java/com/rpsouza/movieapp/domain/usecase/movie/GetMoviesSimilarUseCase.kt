package com.rpsouza.movieapp.domain.usecase.movie

import com.rpsouza.movieapp.data.mapper.toDomain
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.domain.repository.movie.MovieDetailsRepository
import com.rpsouza.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetMoviesSimilarUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {

    suspend operator fun invoke(movieId: Int, apiKey: String, language: String): List<Movie> {
        return movieDetailsRepository.getMoviesSimilar(
            movieId = movieId,
            apiKey = apiKey,
            language = language,
        ).map { it.toDomain() }.filter { it.posterPath != null }
    }
}