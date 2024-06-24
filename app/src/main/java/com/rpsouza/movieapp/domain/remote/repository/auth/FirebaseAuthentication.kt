package com.rpsouza.movieapp.domain.remote.repository.auth

interface FirebaseAuthentication {
  suspend fun login(email: String, password: String)

  suspend fun register(email: String, password: String)

  suspend fun forgot(email: String)
}