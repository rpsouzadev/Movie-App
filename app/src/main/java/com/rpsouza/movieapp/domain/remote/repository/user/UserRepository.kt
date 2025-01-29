package com.rpsouza.movieapp.domain.remote.repository.user

import android.net.Uri
import com.rpsouza.movieapp.domain.model.user.User

interface UserRepository {
    suspend fun updateUser(user: User)

    suspend fun getUser(): User

    suspend fun saveUserImage(uri: Uri): String
}