package com.rpsouza.movieapp.data.remote.repository.movie

import com.rpsouza.movieapp.data.api.ServiceAPI
import com.rpsouza.movieapp.data.remote.model.cast.CastResponse
import com.rpsouza.movieapp.data.remote.model.movie.MovieResponse
import com.rpsouza.movieapp.data.remote.model.review.MovieReviewResponse
import com.rpsouza.movieapp.domain.remote.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
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

    override suspend fun getReviews(
        movieId: Int,
        apiKey: String,
    ): List<MovieReviewResponse> {
        return serviceAPI.getReviews(
            movieId = movieId,
            apiKey = apiKey,
        ).results ?: emptyList()
    }
}