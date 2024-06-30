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
    override suspend fun getMovieDetails(movieId: Int): MovieResponse {
        return serviceAPI.getMovieDetails(movieId = movieId)
    }

    override suspend fun getCredits(movieId: Int): List<CastResponse> {
        return serviceAPI.getCredits(movieId = movieId).cast
    }

    override suspend fun getMoviesSimilar(movieId: Int): List<MovieResponse> {
        return serviceAPI.getMoviesSimilar(movieId = movieId).results ?: emptyList()
    }

    override suspend fun getReviews(movieId: Int): List<MovieReviewResponse> {
        return serviceAPI.getReviews(movieId = movieId).results ?: emptyList()
    }
}