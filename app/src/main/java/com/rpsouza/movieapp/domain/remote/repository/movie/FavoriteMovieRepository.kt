package com.rpsouza.movieapp.domain.remote.repository.movie

import com.rpsouza.movieapp.domain.model.favorite.FavoriteMovie

interface FavoriteMovieRepository {
    suspend fun saveFavoriteMovie(favorites: List<FavoriteMovie>)

    suspend fun getFavoriteMovieList(): List<FavoriteMovie>
}