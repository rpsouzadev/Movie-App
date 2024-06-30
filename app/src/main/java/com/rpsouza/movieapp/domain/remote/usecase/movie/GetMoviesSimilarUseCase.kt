package com.rpsouza.movieapp.domain.remote.usecase.movie

import com.rpsouza.movieapp.data.mapper.toDomain
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.domain.remote.repository.movie.MovieDetailsRepository
import com.rpsouza.movieapp.domain.remote.repository.movie.MovieRepository
import javax.inject.Inject

class GetMoviesSimilarUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {

    suspend operator fun invoke(movieId: Int): List<Movie> {
        return movieDetailsRepository.getMoviesSimilar(movieId = movieId)
            .map { it.toDomain() }.filter { it.posterPath != null }
    }
}