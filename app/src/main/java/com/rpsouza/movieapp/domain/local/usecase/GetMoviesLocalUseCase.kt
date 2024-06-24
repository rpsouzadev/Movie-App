package com.rpsouza.movieapp.domain.local.usecase

import com.rpsouza.movieapp.data.local.entity.MovieEntity
import com.rpsouza.movieapp.data.mapper.toDomain
import com.rpsouza.movieapp.domain.local.repository.MovieLocalRepository
import com.rpsouza.movieapp.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesLocalUseCase @Inject constructor(
    private val repository: MovieLocalRepository
) {

    operator fun invoke(): Flow<List<Movie>> {
        return repository.getMovies().map { movieList ->
            movieList.map { it.toDomain() }
        }
    }
}