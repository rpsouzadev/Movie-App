package com.rpsouza.movieapp.domain.usecase.auth

import com.rpsouza.movieapp.domain.repository.auth.FirebaseAuthentication

class RegisterUseCase(
  private val firebaseAuthentication: FirebaseAuthentication
) {

  suspend operator fun invoke(email: String, password: String) {
    firebaseAuthentication.register(email, password)
  }
}