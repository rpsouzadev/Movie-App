package com.rpsouza.movieapp.domain.remote.usecase.review

import com.rpsouza.movieapp.data.mapper.toDomain
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.domain.model.review.MovieReview
import com.rpsouza.movieapp.domain.remote.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class GetReviewsUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {

    suspend operator fun invoke(movieId: Int, apiKey: String): List<MovieReview> {
        return movieDetailsRepository.getReviews(
            movieId = movieId,
            apiKey = apiKey,
        ).map { it.toDomain() }
    }
}