package com.rpsouza.movieapp.domain.model.user

data class User(
    val id: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val phone: String? = null,
    val gender: String? = null,
    val country: String? = null,
)
