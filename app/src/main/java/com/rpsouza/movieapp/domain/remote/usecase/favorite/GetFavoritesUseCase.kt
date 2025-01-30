package com.rpsouza.movieapp.domain.remote.usecase.favorite

import com.rpsouza.movieapp.domain.model.favorite.FavoriteMovie
import com.rpsouza.movieapp.domain.remote.repository.movie.FavoriteMovieRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val favoriteMovieRepository: FavoriteMovieRepository
) {
    suspend operator fun invoke(): List<FavoriteMovie> {
        return favoriteMovieRepository.getFavoriteMovieList()
    }
}