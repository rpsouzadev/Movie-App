package com.rpsouza.movieapp.data.model.review

import com.google.gson.annotations.SerializedName

data class AuthorDetailsResponse(
    @SerializedName("avatar_path")
    val avatarPath: String?,
    val name: String?,
    val rating: Int?,
    val username: String?
)