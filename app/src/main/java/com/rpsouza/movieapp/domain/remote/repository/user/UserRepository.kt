package com.rpsouza.movieapp.domain.remote.repository.user

import com.rpsouza.movieapp.domain.model.user.User

interface UserRepository {
    suspend fun updateUser(user: User)
}