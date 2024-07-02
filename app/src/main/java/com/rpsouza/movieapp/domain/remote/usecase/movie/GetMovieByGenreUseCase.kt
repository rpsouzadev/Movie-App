package com.rpsouza.movieapp.domain.remote.usecase.movie

import com.rpsouza.movieapp.data.mapper.toDomain
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.domain.remote.repository.movie.MovieRepository
import javax.inject.Inject

class GetMovieByGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(genreId: Int?): List<Movie> {
        return movieRepository.getMovieByGenre(genreId).results?.map {
            it.toDomain()
        } ?: emptyList()
    }
}