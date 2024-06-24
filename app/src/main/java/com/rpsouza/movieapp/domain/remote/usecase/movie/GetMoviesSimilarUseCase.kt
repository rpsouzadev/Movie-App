package com.rpsouza.movieapp.domain.remote.usecase.movie

import com.rpsouza.movieapp.data.mapper.toDomain
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.domain.remote.repository.movie.MovieDetailsRepository
import com.rpsouza.movieapp.domain.remote.repository.movie.MovieRepository
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