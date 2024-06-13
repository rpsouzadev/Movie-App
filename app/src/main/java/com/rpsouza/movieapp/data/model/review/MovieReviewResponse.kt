package com.rpsouza.movieapp.data.model.review

import com.google.gson.annotations.SerializedName

data class MovieReviewResponse(
    val author: String?,

    @SerializedName("author_details")
    val authorDetails: AuthorDetailsResponse?,
    val content: String?,

    @SerializedName("created_at")
    val createdAt: String?,
    val id: String?,

    @SerializedName("updated_at")
    val updatedAt: String?,
    val url: String?
)