package com.rpsouza.movieapp.data.remote.repository.favorite

import com.google.firebase.database.FirebaseDatabase
import com.rpsouza.movieapp.domain.model.favorite.FavoriteMovie
import com.rpsouza.movieapp.domain.remote.repository.movie.FavoriteMovieRepository
import com.rpsouza.movieapp.utils.FirebaseHelper
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class FavoriteMovieRepositoryImpl @Inject constructor(
    firebaseDatabase: FirebaseDatabase
): FavoriteMovieRepository {
    private val favoriteRef = firebaseDatabase.reference
        .child("favorites")

    override suspend fun saveFavoriteMovie(favorites: List<FavoriteMovie>) {
        suspendCoroutine { continuation ->
            favoriteRef
                .child(FirebaseHelper.getUserId())
                .setValue(favorites)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resumeWith(Result.success(Unit))
                    } else {
                        task.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
        }
    }

}