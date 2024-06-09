package com.rpsouza.movieapp.data.repository.movie

import com.rpsouza.movieapp.data.api.ServiceAPI
import com.rpsouza.movieapp.data.model.cast.CastResponse
import com.rpsouza.movieapp.data.model.gener.GenreListResponse
import com.rpsouza.movieapp.data.model.movie.MovieResponse
import com.rpsouza.movieapp.domain.repository.movie.MovieDetailsRepository
import com.rpsouza.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

abstract class MovieDetailsRepositoryImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
) : MovieDetailsRepository {
    override suspend fun getMovieDetails(
        movieId: Int,
        apiKey: String,
        language: String?
    ): MovieResponse {
        return serviceAPI.getMovieDetails(
            movieId = movieId,
            apiKey = apiKey,
            language = language,
        )
    }

    override suspend fun getCredits(
        movieId: Int,
        apiKey: String,
        language: String?
    ): List<CastResponse> {
        return serviceAPI.getCredits(
            movieId = movieId,
            apiKey = apiKey,
            language = language,
        ).cast
    }

    override suspend fun getMoviesSimilar(
        movieId: Int,
        apiKey: String,
        language: String?
    ): List<MovieResponse> {
        return serviceAPI.getMoviesSimilar(
            movieId = movieId,
            apiKey = apiKey,
            language = language,
        ).results ?: emptyList()
    }
}