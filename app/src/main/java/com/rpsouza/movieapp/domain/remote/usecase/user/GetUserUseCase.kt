package com.rpsouza.movieapp.domain.remote.usecase.user

import com.rpsouza.movieapp.domain.model.user.User
import com.rpsouza.movieapp.domain.remote.repository.user.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(): User {
        return userRepository.getUser()
    }

}