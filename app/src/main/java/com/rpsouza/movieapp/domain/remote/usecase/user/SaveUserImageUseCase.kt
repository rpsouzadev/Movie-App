package com.rpsouza.movieapp.domain.remote.usecase.user

import android.net.Uri
import com.rpsouza.movieapp.domain.remote.repository.user.UserRepository
import javax.inject.Inject

class SaveUserImageUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(uri: Uri): String {
        return userRepository.saveUserImage(uri)
    }

}