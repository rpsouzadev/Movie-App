package com.rpsouza.movieapp.domain.local.usecase

import com.rpsouza.movieapp.domain.local.repository.MovieLocalRepository
import javax.inject.Inject

class DeleteMovieLocalUseCase @Inject constructor(
    private val repository: MovieLocalRepository
) {
    suspend operator fun invoke(movieId: Int) {
        repository.deleteMovie(movieId)
    }
}