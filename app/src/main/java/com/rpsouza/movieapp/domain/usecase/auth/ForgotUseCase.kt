package com.rpsouza.movieapp.domain.usecase.auth

import com.rpsouza.movieapp.domain.repository.auth.FirebaseAuthentication

class ForgotUseCase(
  private val firebaseAuthentication: FirebaseAuthentication
) {

  suspend operator fun invoke(email: String) {
    firebaseAuthentication.forgot(email)
  }
}