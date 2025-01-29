package com.rpsouza.movieapp.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String? = "",
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val phone: String? = null,
    val gender: String? = null,
    val country: String? = null,
) : Parcelable
