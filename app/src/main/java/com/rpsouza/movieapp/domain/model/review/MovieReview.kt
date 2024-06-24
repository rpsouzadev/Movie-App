package com.rpsouza.movieapp.domain.model.review

import com.google.gson.annotations.SerializedName
import com.rpsouza.movieapp.data.remote.model.review.AuthorDetailsResponse

data class MovieReview(
    val author: String?,
    val authorDetails: AuthorDetails?,
    val content: String?,
    val createdAt: String?,
    val id: String?,
    val updatedAt: String?,
    val url: String?
)
