package com.rpsouza.movieapp.domain.remote.usecase.user

import com.rpsouza.movieapp.domain.model.user.User
import com.rpsouza.movieapp.domain.remote.repository.user.UserRepository
import javax.inject.Inject

class UserUpdateUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(user: User) {
        userRepository.updateUser(user)
    }

}