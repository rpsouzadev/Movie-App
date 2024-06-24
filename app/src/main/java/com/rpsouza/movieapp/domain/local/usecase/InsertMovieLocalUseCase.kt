package com.rpsouza.movieapp.domain.local.usecase

import com.rpsouza.movieapp.data.mapper.toEntity
import com.rpsouza.movieapp.domain.local.repository.MovieLocalRepository
import com.rpsouza.movieapp.domain.model.movie.Movie
import javax.inject.Inject

class InsertMovieLocalUseCase @Inject constructor(
    private val repository: MovieLocalRepository
) {
    suspend operator fun invoke(movie: Movie) {
        repository.insertMovie(movie.toEntity())
    }
}