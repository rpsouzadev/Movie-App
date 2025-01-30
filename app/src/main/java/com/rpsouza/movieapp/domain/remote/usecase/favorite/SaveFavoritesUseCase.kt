package com.rpsouza.movieapp.domain.remote.usecase.favorite

import com.rpsouza.movieapp.domain.model.favorite.FavoriteMovie
import com.rpsouza.movieapp.domain.remote.repository.movie.FavoriteMovieRepository
import javax.inject.Inject

class SaveFavoritesUseCase @Inject constructor(
    private val favoriteMovieRepository: FavoriteMovieRepository
) {
    suspend operator fun invoke(favorites: List<FavoriteMovie>) {
        favoriteMovieRepository.saveFavoriteMovie(favorites)
    }
}